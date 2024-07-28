package com.kapi.exposed.psql

import com.kapi.reservation.Reservation
import com.kapi.reservation.ReservationStatus
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.postgresql.util.PGobject
import java.time.LocalDateTime

object PsqlDiner : Table("diner") {
    val id: Column<Int> = integer("id")
    val name: Column<String> = varchar("name", length = 300)
    val phoneNumberE164: Column<String> = varchar("phone_number_e164", length = 16)
    override val primaryKey = PrimaryKey(id) // name is optional here
}

object PsqlDietaryRestriction : Table("dietary_restriction") {
    val id: Column<Int> = integer("id")
    val name: Column<String> = varchar("name", length = 100)
    val description: Column<String> = varchar("description", length = 300)
    override val primaryKey = PrimaryKey(id) // name is optional here
}

object PsqlDinerDietaryRestriction : Table("diner_dietary_restriction") {
    val dinerId: Column<Int> = integer("diner_id") references PsqlDiner.id
    val dietaryRestrictionId: Column<Int> = integer("dietary_restriction_id") references PsqlDietaryRestriction.id
}

object PsqlRestaurant : Table("restaurant") {
    val id: Column<Int> = integer("id")
    val name: Column<String> = varchar("name", length = 300)
    override val primaryKey = PrimaryKey(id)
}

object PsqlRestaurantEndorsement : Table("restaurant_endorsement") {
    val restaurantId: Column<Int> = integer("restaurant_id") references PsqlRestaurant.id
    val dietaryRestrictionId: Column<Int> = integer("dietary_restriction_id") references PsqlDietaryRestriction.id
}

object PsqlTable : Table("tables") {
    val id: Column<Int> = integer("id")
    val restaurantId: Column<Int> = integer("restaurant_id") references PsqlRestaurant.id
    val tableId: Column<Int> = integer("table_id")
    val capacity: Column<Int> = integer("capacity")
    override val primaryKey = PrimaryKey(id)
}

class PGEnum<T : Enum<T>>(enumTypeName: String, enumValue: T?) : PGobject() {
    init {
        value = enumValue?.name?.lowercase()
        type = enumTypeName
    }
}

object PsqlReservation : Table("reservation") {
    val id: Column<Int> = integer("id").autoIncrement()
    val datetime: Column<LocalDateTime> = datetime("datetime")
    // See: https://jetbrains.github.io/Exposed/data-types.html#how-to-use-database-enum-types
    val status: Column<ReservationStatus> = customEnumeration(
        "status",
        "reservation_status",
        { value -> ReservationStatus.entries.first { it.value == value } },
        { PGEnum("reservation_status", it) })
    val restaurantId: Column<Int> = integer("restaurant_id")
    val rescheduleReservationId: Column<Int> = integer("reschedule_reservation_id") references PsqlReservation.id

    override val primaryKey = PrimaryKey(id)
}

object PsqlReservationDiner : Table("reservation_diner") {
    val reservationId: Column<Int> = integer("reservation_id") references PsqlReservation.id
    val dinerId: Column<Int> = integer("diner_id") references PsqlDiner.id
}

object PsqlReservationTable : Table("reservation_table") {
    val tableId: Column<Int> = integer("table_id") references PsqlTable.id
    val reservationId: Column<Int> = integer("reservation_id") references PsqlReservation.id
}

