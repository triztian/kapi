package com.kapi.diner

import kotlinx.serialization.Serializable

@Serializable
data class DietaryRestriction(
    val id: Int,
    val name: String,
    val description: String?
)
