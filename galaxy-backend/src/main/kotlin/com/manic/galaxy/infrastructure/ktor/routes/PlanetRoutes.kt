package com.manic.galaxy.infrastructure.ktor.routes

import com.manic.galaxy.application.FacilityService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject
import java.util.*

fun Routing.planetRoutes() {
    val facilityService by inject<FacilityService>()

    authenticate {
        get("/api/planets/{id}/facilities") {
            val id = call.parameters.getOrFail<UUID>("id")
            val facilities = facilityService.listFacilities(id)
            call.respond(facilities)
        }
    }
}
