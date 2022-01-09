package com.manic.galaxy.infrastructure.ktor.plugins

import com.manic.galaxy.infrastructure.exposed.DatabaseFactory
import com.manic.galaxy.infrastructure.ktor.transactions.Transactions
import io.ktor.application.*
import io.ktor.request.*

fun Application.configureTransactions() {
    val host = environment.config.property("galaxy.postgres.host").getString()
    val port = environment.config.property("galaxy.postgres.port").getString().toInt()
    val username = environment.config.property("galaxy.postgres.username").getString()
    val password = environment.config.property("galaxy.postgres.password").getString()
    val name = environment.config.property("galaxy.postgres.database").getString()

    val database = DatabaseFactory.new(host, port, username, password, name)

    install(Transactions) {
        this.database = database
        filter { call -> call.request.path().startsWith("/api") }
    }
}

