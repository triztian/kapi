package com.kapi.server

import com.kapi.diner.DinerRepository
import io.ktor.util.pipeline.*

import com.kapi.reservation.ReservationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

/**
 * A route that allows searching for restaurants that support a party size at a given date time
 */
suspend fun PipelineContext<Unit, ApplicationCall>.routeFindRestaurants(): Unit {
    val dinerStrIds = call.request.queryParameters.getAll("diner")
    if (dinerStrIds == null) {
        call.respond(HttpStatusCode.BadRequest, makeBadRequest("Missing 'diner' parameter"))
        return
    }

    val dinerIds = dinerStrIds.mapNotNull { it.toIntOrNull() }.toSet()
    if (dinerStrIds.size != dinerIds.size) {
        call.respond(HttpStatusCode.BadRequest, makeBadRequest("Invalid 'diner' values $dinerStrIds"))
        return
    }

    val reservationDatetimeStr = call.request.queryParameters["datetime"]
    if (reservationDatetimeStr == null) {
        call.respond(HttpStatusCode.BadRequest, makeBadRequest("Missing 'datetime' parameter"))
        return
    }

    // expect "2015-08-04T10:11:30.000Z"
    val reservationDatetime = try {
        LocalDateTime.parse(reservationDatetimeStr)
    } catch (e: Exception) {
        null
    }

    if (reservationDatetime == null) {
        call.respond(HttpStatusCode.BadRequest, makeBadRequest("Invalid 'datetime' parameter $reservationDatetimeStr"))
        return
    }

    val dinerRepository = getInstance<DinerRepository>()
    val diners = dinerRepository.get(ids = dinerIds.toIntArray())

    val reservationService = getInstance<ReservationService>()
    val results = runBlocking {
        reservationService.find(diners, reservationDatetime)
    }

    call.respond(results)
}