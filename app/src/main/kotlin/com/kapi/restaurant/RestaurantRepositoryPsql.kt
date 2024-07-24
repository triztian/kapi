package com.kapi.restaurant

import com.kapi.diner.DietaryRestriction
import com.kapi.exposed.psql.PsqlDietaryRestriction
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
     * Get a specific restaurant by it's ID. If the restaurant does not exist `null` is returned.
     */
    override suspend fun get(id: Int): Restaurant? {
        return transaction {
            val result =
                PsqlRestaurant.selectAll().where(PsqlRestaurant.id eq id).firstOrNull() ?: return@transaction null
            Restaurant(id, result[PsqlRestaurant.name], getRestaurantTables(id), getRestaurantEndorsements(id))
        }
    }

    /**
     * Finds restaurants that have a given set endorsements and that have a given set of endorsements.
     */
    override suspend fun find(withEndorsements: Set<Int>, withoutEndorsements: Set<Int>): Set<Restaurant> {
        return transaction {
            (PsqlRestaurantEndorsement innerJoin PsqlRestaurant).select(PsqlRestaurant.columns).where(
                (PsqlRestaurantEndorsement.dietaryRestrictionId inList withEndorsements.toList()) and
                        (PsqlRestaurantEndorsement.dietaryRestrictionId notInList withoutEndorsements.toList())
            ).map {
                Restaurant(
                    it[PsqlRestaurant.id],
                    it[PsqlRestaurant.name],
                    getRestaurantTables(it[PsqlRestaurant.id]),
                    getRestaurantEndorsements(it[PsqlRestaurant.id])
                )
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

    /**
     * Obtains a list of the endorsements of a given restaurant.
     */
    private fun getRestaurantEndorsements(restaurantId: Int): Set<DietaryRestriction> {
        return (PsqlRestaurantEndorsement innerJoin PsqlDietaryRestriction).select(PsqlDietaryRestriction.columns)
            .where {
                PsqlRestaurantEndorsement.restaurantId eq restaurantId
            }.map {
                DietaryRestriction(
                    it[PsqlDietaryRestriction.id],
                    it[PsqlDietaryRestriction.name],
                    it[PsqlDietaryRestriction.description]
                )
            }.toSet()
    }
}