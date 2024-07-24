package com.kapi.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: Int,
    val tables: Set<Table>
)