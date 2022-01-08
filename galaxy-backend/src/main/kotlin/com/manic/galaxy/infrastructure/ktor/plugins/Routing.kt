package com.manic.galaxy.infrastructure.ktor.plugins

import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.PlanetService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.configureRouting() {
    install(AutoHeadResponse)

    val galaxyService by inject<GalaxyService>()
    val planetService by inject<PlanetService>()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }
        }

        get("/api/galaxies") {
            val galaxies = galaxyService.listGalaxies()
            call.respond(galaxies)
        }

        post("/api/galaxies") {

        }

        get("/api/galaxies/{id}/planets") {
            val principal = call.principal<UserPrincipal>()!!
            val id = call.parameters.getOrFail<UUID>("id")
            planetService.listPlanets(principal.id, id)
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
