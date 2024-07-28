package com.kapi.exposed.psql

import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.ResultSet

/**
 * A helper to execute raw SQL statements.
 */
fun <T : Any> String.execAndMap(transform: (ResultSet) -> T): List<T> {
    val result = arrayListOf<T>()
    TransactionManager.current().exec(this) { rs ->
        while (rs.next()) {
            result += transform(rs)
        }
    }
    return result
}