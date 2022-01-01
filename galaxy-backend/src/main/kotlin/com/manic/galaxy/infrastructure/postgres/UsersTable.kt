package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.user.UserRole

object UsersTable : EntityTable("users") {
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val role = enumeration("role", UserRole::class)
}
