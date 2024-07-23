package com.kapi.server

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.routeCreateReservation(): Unit {
    call.respondText("Create a reservation for a party of diners")
}
