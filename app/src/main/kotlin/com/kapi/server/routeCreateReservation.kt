package com.kapi.server

import com.kapi.diner.DinerRepository
import com.kapi.reservation.ReservationService
import com.kapi.restaurant.RestaurantRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Payload(
    val restaurantId: Int,
    val dinerIds: Set<Int>,
    val atDatetime: String,
)

suspend fun PipelineContext<Unit, ApplicationCall>.routeCreateReservation(): Unit {
    val input = call.receive<Payload>()

    val atDatetime = try {
        LocalDateTime.parse(input.atDatetime)
    } catch (e: Exception) {
        null
    }
    if (atDatetime == null) {
        call.respond(HttpStatusCode.BadRequest, "Invalid 'atDatetime' ${input.atDatetime}")
        return
    }

    // Load services and repos
    val reservationService = getInstance<ReservationService>()
    val restaurantRepository = getInstance<RestaurantRepository>()
    val dinerRepository = getInstance<DinerRepository>()

    val restaurant = restaurantRepository.get(input.restaurantId)
    if (restaurant == null) {
        call.respond(HttpStatusCode.BadRequest, "Invalid 'restaurantId' ${input.restaurantId}")
        return
    }
    val diners = dinerRepository.get(*input.dinerIds.toIntArray())
    if (diners.size != input.dinerIds.size) {
        call.respond(HttpStatusCode.BadRequest, "Invalid 'dinerIds' ${input.dinerIds}")
        return
    }

    val reservation = reservationService.create(restaurant, diners, atDatetime)
    if (reservation == null) {
        call.respond(HttpStatusCode.BadRequest, "Could not create reservation")
        return
    }
    call.respond(reservation)
}
