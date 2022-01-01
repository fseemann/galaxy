package com.manic.galaxy.domain.galaxy

import com.manic.galaxy.domain.shared.Invariants
import com.manic.galaxy.domain.user.User
import com.manic.galaxy.domain.user.UserRole
import java.util.*

object GalaxyFactory {
    fun new(
        user: User,
        name: String
    ): Galaxy {
        Invariants.require(user.role == UserRole.ADMIN) { "user.notAdmin" }

        return Galaxy(UUID.randomUUID(), name)
    }
}
