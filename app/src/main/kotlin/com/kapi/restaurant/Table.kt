package com.kapi.restaurant

/**
 * A dining table that can sit patrons.
 */
data class Table(
    val id: Int,
    val capacity: Int
)

/**
 * Determines the total capacity of an array of tables
 */
fun Set<Table>.totalCapacity() = this.map { it.capacity }.fold(0) { sum, value -> sum + value }

