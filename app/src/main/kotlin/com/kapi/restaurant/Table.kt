package com.kapi.restaurant

import kotlinx.serialization.Serializable

/**
 * A dining table that can sit patrons.
 */
@Serializable
data class Table(
    val id: Int,
    val capacity: Int
)

/**
 * Determines the total capacity of an array of tables
 */
fun Set<Table>.totalCapacity() = this.map { it.capacity }.fold(0) { sum, value -> sum + value }

