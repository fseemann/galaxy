package com.manic.galaxy.infrastructure.ktor.plugins

import com.manic.galaxy.infrastructure.koin.ModuleFactory
import com.manic.galaxy.infrastructure.mongodb.DatabaseFactory
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun Application.configureDependencyInjection() {
    val mongoDbUri = environment.config.property("galaxy.mongodb.uri").getString()
    val database = DatabaseFactory.new(mongoDbUri)

    install(Koin) {
        module(createdAtStart = true) {
            SLF4JLogger()
            modules(ModuleFactory.new(database))
        }
    }
}
