package com.kapi.restaurant

data class Restaurant(
    val id: Int,
    val tables: Set<Table>
)