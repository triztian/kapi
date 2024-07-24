package com.kapi.server

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val code: Int? = null, val title: String, val message: String)

fun makeBadRequest(message: String) = ErrorResponse(code = null, title = "Bad Request", message = message)
