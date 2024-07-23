package com.kapi.restaurant

import kotlin.test.Test
import kotlin.test.assertEquals

class TableTest {
    @Test
    fun totalCapacity() {
        val tables = setOf(
            Table(0, 1),
            Table(1, 1)
        )

        assertEquals(2, tables.totalCapacity())
    }
}