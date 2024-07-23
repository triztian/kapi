package com.kapi.exposed.psql

import org.jetbrains.exposed.sql.Database

/**
 * Setups the database connection
 *
 * Reads the following env variables (and examples):
 *
 *   - KAPI_JDBC_HOST_PATH -> "localhost:5432/myadatabase"
 *   - KAPI_JDBC_USERNAME -> "myuser"
 *   - KAPI_JDBC_PASSWORD -> "secret"
 */
fun initialize() {
    Database.connect(
        "jdbc:postgresql://${System.getenv("KAPI_JDBC_HOST_PATH")}",
        driver = "org.postgresql.Driver",
        user = System.getenv("KAPI_JDBC_USERNAME"),
        password = System.getenv("KAPI_JDBC_PASSWORD")
    )
}