package com.kapi.restaurant

interface RestaurantRepository {
    /**
     * Get a specific restaurant by it's ID. Returns `null` if not found.
     */
    suspend fun get(id: Int): Restaurant?

    /**
     * Find a set of restaurants that match the given criteria.
     */
    suspend fun find(withEndorsements: Set<Int> = emptySet(),
                     withoutEndorsements: Set<Int> = emptySet()
    ): Set<Restaurant>
}