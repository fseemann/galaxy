package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.shared.EntityRepository
import java.util.*

interface PlanetRepository : EntityRepository<Planet> {
    suspend fun insert(planets: List<Planet>)
    suspend fun getUnowned(galaxyId: UUID): Planet
    suspend fun requireNotOwner(userId: UUID)
    suspend fun list(
        galaxyId: UUID,
        userId: UUID,
    ): List<Planet>
}
