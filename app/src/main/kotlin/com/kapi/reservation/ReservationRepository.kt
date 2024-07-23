package com.kapi.reservation

import com.kapi.diner.Diner
import com.kapi.restaurant.Restaurant
import java.time.LocalDateTime

/**
 *
 */
interface ReservationRepository {
    /**
     * Gets a specific reservation by its ID.
     */
    suspend fun get(id: Int): Reservation

    /**
     * Performs reservation lookups with simple matching options.
     */
    suspend fun find(
        vararg restaurants: Restaurant,
        atDatetime: LocalDateTime? = null,
        withStatus: Set<ReservationStatus> = emptySet(),
        forPartySize: Int? = 1
    ): Set<Reservation>

    /**
     *
     */
    suspend fun create(
        restaurant: Restaurant,
        diners: Set<Diner>,
        atDatetime: LocalDateTime
    ): Reservation
}