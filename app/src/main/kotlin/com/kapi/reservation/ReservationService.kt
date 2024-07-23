package com.kapi.reservation

import com.kapi.diner.Diner
import com.kapi.restaurant.Restaurant
import java.time.LocalDateTime

interface ReservationService {
    /**
     * Search for restaurants that have capacity for the party size given by the diner IDs at
     * the given date time.
     */
    suspend fun find(
        diners: Set<Diner>,
        datetime: LocalDateTime,
        considerDietaryRestrictions: Boolean = true
    ): Set<Restaurant>

    /**
     * Create a new reservation at a specific restaurant with a set of diners at a date and time
     */
    suspend fun create(restaurant: Restaurant, diners: Set<Diner>, datetime: LocalDateTime): Reservation
}