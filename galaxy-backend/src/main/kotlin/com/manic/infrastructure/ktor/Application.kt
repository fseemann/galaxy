package com.manic.infrastructure.ktor

import com.manic.infrastructure.ktor.plugins.*
import io.ktor.application.*

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
}
