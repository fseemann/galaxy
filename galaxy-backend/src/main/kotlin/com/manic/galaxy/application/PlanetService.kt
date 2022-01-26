package com.manic.galaxy.application

import com.manic.galaxy.domain.planet.Planet
import com.manic.galaxy.domain.planet.PlanetRepository
import java.util.*

class PlanetService(
    private val planetRepository: PlanetRepository,
) {

    suspend fun listPlanets(
        userId: UUID,
        galaxyId: UUID,
    ): List<Planet> {
        return planetRepository.list(galaxyId, userId)
    }

    suspend fun updateStorage(planetId: UUID): Planet {
        val planet = planetRepository.get(planetId)
        planet.updateStorage()
        return planetRepository.update(planet)
    }

    suspend fun get(planetId: UUID): Planet {
        return planetRepository.get(planetId)
    }
}
