package com.kapi.reservation

import com.kapi.diner.Diner
import com.kapi.restaurant.Restaurant
import java.time.LocalDateTime

class ReservationRepositoryPsql: ReservationRepository {
    override suspend fun get(id: Int): Reservation {
        TODO("Not yet implemented")
    }

    override suspend fun find(
        vararg restaurants: Restaurant,
        atDatetime: LocalDateTime?,
        withStatus: Set<ReservationStatus>,
        forPartySize: Int?
    ): Set<Reservation> {
        TODO("Not yet implemented")
    }

    override suspend fun create(restaurant: Restaurant, diners: Set<Diner>, atDatetime: LocalDateTime): Reservation {
        TODO("Not yet implemented")
    }
}