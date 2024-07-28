package com.kapi.reservation

import com.kapi.restaurant.Restaurant
import com.kapi.restaurant.Table
import com.kapi.diner.Diner
import com.kapi.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 *
 */
@Serializable
enum class ReservationStatus(val value: String) {
    Pending("pending"),
    Confirmed("confirmed"),
    Finalized("finalized"),
    Rescheduled("rescheduled"),
    Cancelled("cancelled"),
    Abandoned("abandoned")
}

/**
 * A reservation at a restaurant on a set of tables.
 */
@Serializable
data class Reservation(
    val id: Int,
    val status: ReservationStatus,
    @Serializable(with = LocalDateTimeSerializer::class)
    val datetime: LocalDateTime,
    val restaurant: Restaurant,
    val tables: Set<Table>,
    val diners: Set<Diner>
)
