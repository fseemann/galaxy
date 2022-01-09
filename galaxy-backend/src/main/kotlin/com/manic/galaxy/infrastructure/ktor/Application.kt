package com.manic.galaxy.infrastructure.ktor

import com.manic.galaxy.infrastructure.ktor.plugins.*
import com.manic.galaxy.infrastructure.ktor.routing.configureRouting
import com.manic.galaxy.infrastructure.ktor.security.configureSecurity
import io.ktor.application.*

@Suppress("unused")
fun Application.module() {
    configureDependencyInjection()
    configureTransactions()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
