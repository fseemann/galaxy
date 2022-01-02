package com.manic.galaxy.context

import com.manic.galaxy.application.UserService
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class Empty() : KoinComponent {
    fun `when the system creates an admin`(
        email: String = "admin@galaxy.com",
        password: String = "admin",
    ) {
        val userService by inject<UserService>()

        transaction {
            userService.createAdmin(email, password)
        }
    }

    fun `when a user signs in`(
        email: String,
        password: String,
    ) {
        val userService by inject<UserService>()

        transaction {
            userService.signIn(email, password)
        }
    }
}

