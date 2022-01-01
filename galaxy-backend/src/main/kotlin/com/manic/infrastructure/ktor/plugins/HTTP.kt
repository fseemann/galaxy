package com.manic.infrastructure.ktor.plugins

import io.ktor.application.*
import io.ktor.features.*

fun Application.configureHTTP() {
    install(Compression)
    install(DefaultHeaders)
}
