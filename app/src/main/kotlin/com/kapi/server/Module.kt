package com.kapi.server

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Helps retrieve an instance for a type setup by dependency injection.
 */
inline fun <reified T> getInstance(): T = object : KoinComponent {
    val value: T by inject()
}.value
