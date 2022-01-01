package com.manic.galaxy.infrastructure.ktor

import com.manic.galaxy.infrastructure.ktor.plugins.*
import io.ktor.application.*

@Suppress("unused")
fun Application.module() {
    configureDependencyInjection()
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
}
