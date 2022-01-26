package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.galaxy.Galaxy
import com.manic.galaxy.domain.shared.Invariants
import java.util.*

object PlanetFactory {

    fun new(
        galaxy: Galaxy,
        planetCount: Int
    ): List<Planet> {
        Invariants.require(planetCount > 0) { "galaxy.planetCountLessThanOrEqualToZero" }

        return (0..planetCount).map {
            Planet(UUID.randomUUID(), galaxy.id, null, "Planet", emptyList())
        }
    }
}
