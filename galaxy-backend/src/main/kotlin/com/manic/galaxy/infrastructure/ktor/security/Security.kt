package com.manic.galaxy.infrastructure.ktor.security

import com.manic.galaxy.application.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.sessions.*
import org.koin.ktor.ext.inject
import kotlin.collections.set

fun Application.configureSecurity() {
    val userService by inject<UserService>()

    install(Sessions) {
        cookie<UserSession>("user-session", SessionStorageMemory()) {
            cookie.path = "/api"
            cookie.extensions["SameSite"] = "lax"
        }
    }

    authentication {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate {
                val user = userService.signIn(it.name, it.password)
                FormPrincipal(user.id)
            }
        }

        session<UserSession> {
            validate {
                UserPrincipal(it.userId)
            }
        }
    }
}

