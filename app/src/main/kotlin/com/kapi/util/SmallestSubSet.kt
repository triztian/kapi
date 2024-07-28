package com.kapi.util

/**
 * Find the smallest subset of numbers that when added are equal or greater than a given value
 */
fun findSmallestSubset(x: Int, list: List<Int>): List<Int> = g(list, x, mutableMapOf())

/**
 * Based on the implementation below:
 *
 * https://stackoverflow.com/a/3421173
 */
private fun f(items: List<Int>, i: Int, S: Int, memo: MutableMap<Pair<Int, Int>, Int>): Int {
    if (i >= items.size)
        return if (S == 0) 1 else 0

    if (!memo.containsKey(Pair(i, S))) {
        var count = f(items, i + 1, S, memo)
        count += f(items, i + 1, S - items[i], memo)
        memo[Pair(i, S)] = count
    }

    return memo[Pair(i, S)]!!
}

private fun g(v: List<Int>, S: Int, memo: MutableMap<Pair<Int, Int>, Int>): List<Int> {
    val subset = mutableListOf<Int>()
    var s = S
    v.forEachIndexed { i, x ->
        if (f(v, i + 1, s - x, memo) > 0) {
            subset.add(x)
            s -= x
        }
    }

    return subset
}