package com.manic.galaxy.domain.user

import java.util.*

object UserFactory {
    fun newAdmin(
        email: String,
        password: String,
    ): User {
        return User(
            UUID.randomUUID(),
            email,
            password,
            UserRole.ADMIN
        )
    }
}
