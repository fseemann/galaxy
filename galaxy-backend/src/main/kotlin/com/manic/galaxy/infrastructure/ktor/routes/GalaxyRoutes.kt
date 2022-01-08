package com.manic.galaxy.infrastructure.ktor.routes

import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.PlanetService
import com.manic.galaxy.infrastructure.ktor.plugins.UserPrincipal
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject
import java.util.*

fun Routing.galaxyRoutes() {
    val galaxyService by inject<GalaxyService>()
    val planetService by inject<PlanetService>()

    get("/api/galaxies") {
        val galaxies = galaxyService.listGalaxies()
        call.respond(galaxies)
    }

    authenticate {
        post("/api/galaxies") {
            val principal = call.principal<UserPrincipal>()!!
            val input = call.receive<GalaxyCreateInput>()
            val galaxy = galaxyService.createGalaxy(principal.id, input.name, input.planetCount)
            call.respond(galaxy)
        }

        get("/api/galaxies/{id}/planets") {
            val principal = call.principal<UserPrincipal>()!!
            val id = call.parameters.getOrFail<UUID>("id")
            val planets = planetService.listPlanets(principal.id, id)
            call.respond(planets)
        }
    }
}

data class GalaxyCreateInput(
    val name: String,
    val planetCount: Int,
)
