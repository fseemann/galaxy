package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.shared.EntityRepository
import java.util.*

interface PlanetRepository : EntityRepository<Planet> {
    fun insert(planets: List<Planet>)
    fun getUnowned(galaxyId: UUID): Planet
    fun requireNotOwner(userId: UUID)
}
