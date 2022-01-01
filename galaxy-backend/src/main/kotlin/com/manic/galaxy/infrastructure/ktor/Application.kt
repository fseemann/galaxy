package com.manic.galaxy.infrastructure.ktor

import com.manic.galaxy.infrastructure.ktor.plugins.*
import io.ktor.application.*

@Suppress(
    "unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureDependencyInjection()
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
}
