package com.kapi.restaurant

import com.kapi.exposed.psql.PsqlRestaurant
import com.kapi.exposed.psql.PsqlRestaurantEndorsement
import com.kapi.exposed.psql.PsqlTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.notInList
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class RestaurantRepositoryPsql : RestaurantRepository {
    /**
     *
     */
    override suspend fun get(id: Int): Restaurant? {
        return transaction {
            PsqlRestaurant.selectAll().where(PsqlRestaurant.id eq id).firstOrNull() ?: return@transaction null
            Restaurant(id, getRestaurantTables(id))
        }
    }

    /**
     *
     */
    override suspend fun find(withEndorsements: Set<Int>, withoutEndorsements: Set<Int>): Set<Restaurant> {
        return transaction {
            (PsqlRestaurantEndorsement innerJoin PsqlRestaurant).select(PsqlRestaurant.columns).where(
                (PsqlRestaurantEndorsement.dietaryRestrictionId inList withEndorsements.toList()) and
                        (PsqlRestaurantEndorsement.dietaryRestrictionId notInList withoutEndorsements.toList())
            ).map {
                Restaurant(it[PsqlRestaurant.id], getRestaurantTables(it[PsqlRestaurant.id]))
            }.toSet()
        }
    }

    /**
     *
     */
    private fun getRestaurantTables(restaurantId: Int): Set<Table> {
        return (PsqlTable innerJoin PsqlRestaurant).selectAll().where(PsqlRestaurant.id eq restaurantId).map {
            Table(it[PsqlTable.id], it[PsqlTable.capacity])
        }.toSet()
    }
}