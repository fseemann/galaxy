package com.manic.galaxy.application

import com.manic.galaxy.domain.galaxy.Galaxy
import com.manic.galaxy.domain.galaxy.GalaxyFactory
import com.manic.galaxy.domain.galaxy.GalaxyRepository
import com.manic.galaxy.domain.planet.PlanetFactory
import com.manic.galaxy.domain.planet.PlanetRepository
import com.manic.galaxy.domain.user.UserRepository
import java.util.*

class GalaxyService(
    private val userRepository: UserRepository,
    private val galaxyRepository: GalaxyRepository,
    private val planetRepository: PlanetRepository
) {

    /**
     * Creates a new galaxy with planets.
     *
     * @throws com.manic.galaxy.domain.shared.BusinessException if the user is not an admin.
     */
    fun createGalaxy(
        userId: UUID,
        name: String,
        planetCount: Int
    ): Galaxy {
        val user = userRepository.get(userId)

        val galaxy = GalaxyFactory.new(user, name)
        val planets = PlanetFactory.new(galaxy, planetCount)

        galaxyRepository.insert(galaxy)
        planetRepository.insert(planets)
        return galaxy
    }
}
