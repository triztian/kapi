package com.kapi.diner

interface DinerRepository {
    /**
     * Gets a single diner. Returns null if not found.
     */
    suspend fun get(id: Int): Diner?

    /**
     * Gets a list of diners.
     */
    suspend fun get(vararg ids: Int): Set<Diner>

    /**
     *
     */
    suspend fun list(forDiner: Diner? = null): Set<DietaryRestriction>
}