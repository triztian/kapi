package com.kapi.restaurant

import com.kapi.diner.DietaryRestriction
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: Int,
    val name: String,
    val tables: Set<Table>,
    val endorsements: Set<DietaryRestriction>
)