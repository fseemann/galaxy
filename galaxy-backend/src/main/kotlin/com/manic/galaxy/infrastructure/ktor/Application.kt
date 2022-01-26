package com.manic.galaxy.infrastructure.ktor

import com.manic.galaxy.application.UserService
import com.manic.galaxy.infrastructure.ktor.plugins.configureDependencyInjection
import com.manic.galaxy.infrastructure.ktor.plugins.configureHTTP
import com.manic.galaxy.infrastructure.ktor.plugins.configureMonitoring
import com.manic.galaxy.infrastructure.ktor.plugins.configureSerialization
import com.manic.galaxy.infrastructure.ktor.routing.configureRouting
import com.manic.galaxy.infrastructure.ktor.security.configureSecurity
import io.ktor.application.*
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject

@Suppress("unused")
fun Application.module() {
    configureDependencyInjection()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()

    val userService by inject<UserService>()
    kotlin.runCatching {
        runBlocking {
            userService.createAdmin("admin@galaxy.com", "admin")
        }
    }
}
