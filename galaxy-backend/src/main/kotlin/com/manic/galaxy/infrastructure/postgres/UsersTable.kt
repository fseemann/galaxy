package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.user.UserRole

object UsersTable : EntityTable("users") {
    override val id = uuid("id")
    val email = varchar("email", 255).index()
    val password = varchar("password", 255)
    val role = enumeration("role", UserRole::class)
}
