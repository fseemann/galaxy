package com.manic.galaxy.context

import com.manic.galaxy.application.UserService
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Empty() : KoinComponent {
    fun `when the system creates an admin`() {
        val userService by inject<UserService>()

        transaction {
            userService.createAdmin("admin@manic.com", "admin")
        }
    }
}
