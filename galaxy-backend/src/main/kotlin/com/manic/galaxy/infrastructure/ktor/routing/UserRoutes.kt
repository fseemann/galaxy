package com.manic.galaxy.infrastructure.ktor.routing

import com.manic.galaxy.application.UserService
import com.manic.galaxy.domain.user.User
import com.manic.galaxy.domain.user.UserRole
import com.manic.galaxy.infrastructure.ktor.security.FormPrincipal
import com.manic.galaxy.infrastructure.ktor.security.UserSession
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.koin.ktor.ext.inject
import java.util.*

fun Routing.userRoutes() {
    val userService: UserService by inject()

    authenticate("auth-form") {
        post("/api/users/login") {
            val principal = call.principal<FormPrincipal>()!!
            val user = userService.getUser(principal.userId).let(::UserOutDto)
            call.sessions.set(UserSession(principal.userId))
            call.respond(user)
        }
    }

    authenticate {
        post("/api/users/logout") {
            call.sessions.clear<UserSession>()
            call.respond(HttpStatusCode.OK)
        }
    }
}

data class UserOutDto(
    val id: UUID,
    val email: String,
    val role: UserRole
) {
    constructor(user: User) : this(
        user.id,
        user.email,
        user.role
    )
}