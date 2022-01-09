package com.manic.galaxy.infrastructure.ktor

import com.manic.galaxy.application.UserService
import com.manic.galaxy.infrastructure.ktor.plugins.*
import com.manic.galaxy.infrastructure.ktor.routing.configureRouting
import com.manic.galaxy.infrastructure.ktor.security.configureSecurity
import io.ktor.application.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject

@Suppress("unused")
fun Application.module() {
    configureDependencyInjection()
    configureTransactions()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()

    val userService by inject<UserService>()
    kotlin.runCatching {
        transaction {
            userService.createAdmin("admin@galaxy.com", "admin")
        }
    }
}
