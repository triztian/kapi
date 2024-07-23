package com.kapi.diner

import kotlinx.serialization.Serializable

/**
 * A patron or person that will dine in a restaurant.
 */
@Serializable
data class Diner(
    val id: Int,
    val name: String,
    val phoneNumberE164: String?,
    var dietaryRestrictions: Set<DietaryRestriction>
)