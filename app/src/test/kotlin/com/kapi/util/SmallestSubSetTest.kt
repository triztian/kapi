package com.kapi.util

import kotlin.test.Test
import kotlin.test.assertEquals as assertEquals1

class SmallestSubSetTest {
    @Test
    fun singleItem() {
        val input = listOf(2)
        val expected = listOf(2)
        val result = findSmallestSubset(2, input)
        assertEquals1(expected, result)
    }

    @Test
    fun multipleExactItems() {
        val input = listOf(1, 1)
        val expected = listOf(1, 1)
        val result = findSmallestSubset(2, input)
        assertEquals1(expected, result)
    }

    @Test
    fun multipleMatchItems() {
        val input = listOf(2, 2, 4, 4)
        val expected = listOf(2, 2)
        val result = findSmallestSubset(3, input)
        assertEquals1(expected, result)
    }
}