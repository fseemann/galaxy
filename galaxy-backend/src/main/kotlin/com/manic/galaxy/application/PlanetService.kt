package com.manic.galaxy.application

import com.manic.galaxy.domain.planet.Planet
import com.manic.galaxy.domain.planet.PlanetRepository
import java.util.*

class PlanetService(
    private val planetRepository: PlanetRepository,
) {

    fun listPlanets(
        userId: UUID,
        galaxyId: UUID,
    ): List<Planet> {
        return planetRepository.list(galaxyId, userId)
    }
}
