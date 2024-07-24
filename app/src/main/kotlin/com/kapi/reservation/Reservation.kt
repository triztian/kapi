package com.kapi.reservation

import com.kapi.restaurant.Restaurant
import com.kapi.restaurant.Table
import com.kapi.diner.Diner
import java.time.LocalDateTime

/**
 *
 */
enum class ReservationStatus(val value: String) {
    Pending("pending"),
    Confirmed("confirmed"),
    Finalized("finalized"),
    Rescheduled("rescheduled"),
    Cancelled("cancelled"),
    Abandoned("abandoned")
}

/**
 *
 */
data class Reservation(
    val id: Int,
    val status: ReservationStatus,
    val datetime: LocalDateTime,
    val restaurant: Restaurant,
    val tables: Set<Table>,
    val diners: Set<Diner>
)
