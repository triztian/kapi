package com.kapi.exposed.psql

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object PsqlDiner : Table("diner") {
    val id: Column<Int> = integer("id")
    val name: Column<String> = varchar("name", length = 300)
    val phoneNumberE164: Column<String> = varchar("phone_number_e164", length = 16)
    override val primaryKey = PrimaryKey(id) // name is optional here
}

object PsqlDietaryRestriction: Table("dietary_restriction") {
    val id: Column<Int> = integer("id")
    val name: Column<String> = varchar("name", length = 100)
    val description: Column<String> = varchar("description", length = 300)
    override val primaryKey = PrimaryKey(id) // name is optional here
}

object PsqlDinerDietaryRestriction : Table("diner_dietary_restriction") {
    val dinerId : Column<Int> = integer("diner_id") references PsqlDiner.id
    val dietaryRestrictionId: Column<Int> = integer("dietary_restriction_id") references PsqlDietaryRestriction.id
}

object PsqlRestaurant : Table("restaurant") {
    val id: Column<Int> = integer("id")
    val name: Column<String> = varchar("name", length = 300)
    override val primaryKey = PrimaryKey(id)
}

object PsqlRestaurantEndorsement : Table("restaurant_endorsement") {
    val restaurantId: Column<Int> = integer("restaurant_id")
    val dietaryRestrictionId: Column<String> = varchar("dietary_restriction_id", length = 300)
}

object PsqlTable : Table("tables") {
    val id: Column<Int> = integer("id")
    val restaurantId: Column<Int> = integer("restaurant_id") references PsqlRestaurant.id
    val capacity: Column<Int> = integer("capacity")
    override val primaryKey = PrimaryKey(id)
}

