package com.manic.galaxy.infrastructure.ktor.plugins

import com.manic.galaxy.infrastructure.exposed.DatabaseFactory
import com.manic.galaxy.infrastructure.ktor.transactions.Transactions
import io.ktor.application.*
import io.ktor.request.*

fun Application.configureTransactions() {
    val database = DatabaseFactory.new("localhost", 5432, "postgres", "postgres", "galaxy")

    install(Transactions) {
        this.database = database
        filter { call -> call.request.path().startsWith("/api") }
    }
}

