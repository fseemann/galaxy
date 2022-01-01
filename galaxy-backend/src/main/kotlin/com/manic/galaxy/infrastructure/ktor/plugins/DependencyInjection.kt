package com.manic.galaxy.infrastructure.ktor.plugins

import com.manic.galaxy.infrastructure.koin.ModuleFactory
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun Application.configureDependencyInjection() {
    install(Koin) {
        module(createdAtStart = true) {
            SLF4JLogger()
            modules(ModuleFactory.new())
        }
    }
}
