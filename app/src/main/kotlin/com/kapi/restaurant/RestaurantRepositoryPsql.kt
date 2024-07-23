package com.kapi.restaurant

import com.kapi.exposed.psql.PsqlRestaurant
import com.kapi.exposed.psql.PsqlTable
import com.kapi.restaurant.RestaurantRepository
import com.kapi.restaurant.Table
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll

class RestaurantRepositoryPsql : RestaurantRepository {
    override suspend fun get(id: Int): Restaurant? {
        PsqlRestaurant.selectAll().where(PsqlRestaurant.id eq id).firstOrNull() ?: return null
        return Restaurant(id, getRestaurantTables(id))
    }

    override suspend fun find(withEndorsements: Set<Int>, withoutEndorsements: Set<Int>): Set<Restaurant> {
        TODO("Not yet implemented")
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