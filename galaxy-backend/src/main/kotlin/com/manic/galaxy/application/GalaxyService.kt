package com.manic.galaxy.application

import com.manic.galaxy.domain.galaxy.Galaxy
import com.manic.galaxy.domain.galaxy.GalaxyFactory
import com.manic.galaxy.domain.galaxy.GalaxyRepository
import com.manic.galaxy.domain.planet.Planet
import com.manic.galaxy.domain.planet.PlanetFactory
import com.manic.galaxy.domain.planet.PlanetRepository
import com.manic.galaxy.domain.user.UserRepository
import java.util.*

class GalaxyService(
    private val userRepository: UserRepository,
    private val galaxyRepository: GalaxyRepository,
    private val planetRepository: PlanetRepository,
) {

    /**
     * Creates a new galaxy with planets.
     *
     * @throws com.manic.galaxy.domain.shared.BusinessException if the user is not an admin.
     */
    suspend fun createGalaxy(
        userId: UUID,
        name: String,
        planetCount: Int,
    ): Galaxy {
        val user = userRepository.get(userId)

        val galaxy = GalaxyFactory.new(user, name)
        val planets = PlanetFactory.new(galaxy, planetCount)

        galaxyRepository.insert(galaxy)
        planetRepository.insert(planets)
        return galaxy
    }

    suspend fun listGalaxies(): List<Galaxy> {
        return galaxyRepository.list()
    }

    /**
     * Assigns an open planet to the user.
     *
     * @return the user's newly owned planet
     * @throws com.manic.galaxy.domain.shared.BusinessException if player has already joined the galaxy before
     * @throws com.manic.galaxy.domain.shared.BusinessException if the galaxy has no own-able planets anymore.
     */
    suspend fun joinGalaxy(
        userId: UUID,
        galaxyId: UUID,
    ): Planet {
        planetRepository.requireNotOwner(userId)
        val user = userRepository.get(userId)
        val planet = planetRepository.getUnowned(galaxyId)

        planet.transferOwnershipTo(user)

        return planetRepository.update(planet)
    }
}
