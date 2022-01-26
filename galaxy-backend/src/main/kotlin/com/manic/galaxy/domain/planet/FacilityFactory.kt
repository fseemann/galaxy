package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.shared.GalaxyTime
import java.util.*

object FacilityFactory {
    fun newMine(planetId: UUID): Mine {
        return Mine(
            UUID.randomUUID(),
            planetId,
            GalaxyTime.now(),
            1
        )
    }

    fun newStorage(planetId: UUID): Storage {
        val createdAt = GalaxyTime.now()
        return Storage(
            UUID.randomUUID(),
            planetId,
            createdAt,
            0,
            10_000,
            createdAt
        )
    }
}
