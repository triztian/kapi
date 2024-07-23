package com.kapi

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

import com.kapi.diner.DinerRepository
import com.kapi.diner.DinerRepositoryPsql
import com.kapi.restaurant.RestaurantRepository
import com.kapi.restaurant.RestaurantRepositoryPsql
import com.kapi.reservation.ReservationService
import com.kapi.reservation.SimpleReservationService
import com.kapi.reservation.ReservationRepository
import com.kapi.reservation.ReservationRepositoryPsql

val appModule = module {
    // Repositories
    singleOf(::DinerRepositoryPsql) { bind<DinerRepository>() }
    singleOf(::RestaurantRepositoryPsql) { bind<RestaurantRepository>() }
    singleOf(::ReservationRepositoryPsql) { bind<ReservationRepository>() }

    // Services
    singleOf(::SimpleReservationService) { bind<ReservationService>() }
}
