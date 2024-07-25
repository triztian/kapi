package com.kapi.reservation

import java.time.LocalDateTime
import kotlinx.coroutines.*

import com.kapi.diner.Diner
import com.kapi.diner.DinerRepository
import com.kapi.restaurant.Restaurant
import com.kapi.restaurant.RestaurantRepository
import com.kapi.restaurant.totalCapacity

/**
 *
 */
class SimpleReservationService(
    private val dinerRepository: DinerRepository,
    private val restaurantRepository: RestaurantRepository,
    private val reservationRepository: ReservationRepository
) : ReservationService {

    /**
     *
     */
    override suspend fun find(
        diners: Set<Diner>, datetime: LocalDateTime, considerDietaryRestrictions: Boolean
    ): Set<Restaurant> {
        // Get a set of the restrictions to exclude against for
        val dinerIds = diners.map { it.id }.toIntArray()
        val dietaryRestrictionIds = dinerRepository.get(ids = dinerIds).flatMap {
            it.dietaryRestrictions.map { it.id }
        }.toSet()
        println("Dietary Restrictions: $dietaryRestrictionIds")

        // Get restaurants that have endorsements against the restrictions
        var restaurants = restaurantRepository.find(dietaryRestrictionIds)
        println("Restaurants $restaurants")

        // Get existing reservations at the specified time for the qualifying restaurants
        val existingReservations = reservationRepository.find(
            restaurants = restaurants.toTypedArray(),
            withStatus = setOf(
                ReservationStatus.Confirmed,
                ReservationStatus.Pending
            ),
            atDatetime = datetime
        ).toSet()

        // Filter out restaurants that matched a reservation
        val existingReservationIds = existingReservations.map { it.restaurant.id }
        restaurants = restaurants.filter {
            !existingReservationIds.contains(it.id)
        }.toSet()

        // Finally, filter out restaurants that no longer have capacity for
        // the party size. First we calculate the reserved tables and then we subtract the reserved from the total
        // capacity
        val reservationsByRestaurant =
            existingReservations.fold(mutableMapOf<Restaurant, Int>()) { result, reservation ->
                result[reservation.restaurant] = reservation.tables.totalCapacity()
                result
            }
        restaurants = restaurants.filter {
            it.tables.totalCapacity() - reservationsByRestaurant.getOrDefault(it, 0) >= diners.size
        }.toSet()

        return restaurants
    }

    /**
     * Create a reservation. Throws an exception if not created successfully
     */
    override suspend fun create(restaurant: Restaurant, diners: Set<Diner>, datetime: LocalDateTime): Reservation? {
        return reservationRepository.create(restaurant, diners, datetime)
    }
}