package com.manic.galaxy.infrastructure.ktor.routing

import com.manic.galaxy.domain.shared.BusinessException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    install(AutoHeadResponse)

    install(StatusPages) {
        exception<BusinessException> { cause ->
            log.debug(cause.error)
            call.respond(HttpStatusCode.BadRequest, cause.error)
        }
        exception<Exception> { cause ->
            log.error("Something went wrong.", cause)
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "")
        }
    }

    routing {
        userRoutes()
        galaxyRoutes()
    }
}

