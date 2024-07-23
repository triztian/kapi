package com.kapi.reservation

import com.kapi.restaurant.Restaurant
import com.kapi.restaurant.Table
import com.kapi.diner.Diner
import java.time.LocalDateTime

/**
 *
 */
enum class ReservationStatus {
    Pending,
    Confirmed,
    Finalized,
    Rescheduled,
    Cancelled,
    Abandoned
}

/**
 *
 */
data class Reservation(
    val id: Int,
    val status: ReservationStatus,
    val time: LocalDateTime,
    val restaurant: Restaurant,
    val tables: Set<Table>,
    val diners: Set<Diner>
)
