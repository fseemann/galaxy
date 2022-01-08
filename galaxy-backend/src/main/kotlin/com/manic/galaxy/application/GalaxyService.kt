package com.manic.galaxy.application

import com.manic.galaxy.domain.facility.FacilityFactory
import com.manic.galaxy.domain.facility.FacilityRepository
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
    private val facilityRepository: FacilityRepository,
) {

    /**
     * Creates a new galaxy with planets.
     *
     * @throws com.manic.galaxy.domain.shared.BusinessException if the user is not an admin.
     */
    fun createGalaxy(
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

    fun listGalaxies(): List<Galaxy> {
        return galaxyRepository.list()
    }

    /**
     * Assigns an open planet to the user.
     *
     * @return the user's newly owned planet
     * @throws com.manic.galaxy.domain.shared.BusinessException if player has already joined the galaxy before
     * @throws com.manic.galaxy.domain.shared.BusinessException if the galaxy has no own-able planets anymore.
     */
    fun joinGalaxy(
        userId: UUID,
        galaxyId: UUID,
    ): Planet {
        planetRepository.requireNotOwner(userId)
        val user = userRepository.get(userId)
        val planet = planetRepository.getUnowned(galaxyId)

        val mine = FacilityFactory.newMine(planet.id)
        val storage = FacilityFactory.newStorage(planet.id)
        planet.transferOwnershipTo(user)

        facilityRepository.insert(mine, storage)
        return planetRepository.update(planet)
    }
}
