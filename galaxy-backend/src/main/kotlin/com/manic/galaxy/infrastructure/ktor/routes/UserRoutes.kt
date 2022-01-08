package com.manic.galaxy.infrastructure.ktor.routes

import com.manic.galaxy.infrastructure.ktor.plugins.FormPrincipal
import com.manic.galaxy.infrastructure.ktor.plugins.UserSession
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

fun Routing.userRoutes() {
    authenticate("auth-form") {
        post("/api/users/login") {
            val principal = call.principal<FormPrincipal>()!!
            call.sessions.set(UserSession(principal.userId))
            call.respond(HttpStatusCode.OK)
        }
    }

    authenticate {
        post("/api/users/logout") {
            call.sessions.clear<UserSession>()
        }
    }
}
