package com.kapi.server

import com.kapi.diner.DinerRepository
import io.ktor.util.pipeline.*

import com.kapi.reservation.ReservationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.time.LocalDateTime

/**
 *
 */
suspend fun PipelineContext<Unit, ApplicationCall>.routeFindRestaurants(): Unit {
    val dinerStrIds = call.request.queryParameters.getAll("diner")
    if (dinerStrIds == null) {
        call.respond(HttpStatusCode.BadRequest)
        return
    }

    val dinerIds = dinerStrIds.mapNotNull { it.toIntOrNull() }.toSet()
    if (dinerStrIds.size != dinerIds.size) {
        call.respond(HttpStatusCode.BadRequest)
        return
    }

    val reservationDatetimeStr = call.request.queryParameters["datetime"]
    if (reservationDatetimeStr == null) {
        call.respond(HttpStatusCode.BadRequest)
        return
    }

    // expect "2015-08-04T10:11:30"
    val reservationDatetime = try {
        LocalDateTime.parse(reservationDatetimeStr)
    } catch (e : Exception) {
        null
    }

    if (reservationDatetime == null) {
        call.respond(HttpStatusCode.BadRequest)
        return
    }

    val dinerRepository = getInstance<DinerRepository>()
    val diners = dinerRepository.get(ids = dinerIds.toIntArray())

    val reservationService = getInstance<ReservationService>()
    val results = reservationService.find(diners, reservationDatetime)

    call.respond(results)
}