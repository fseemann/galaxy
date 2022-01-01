package com.manic.galaxy.domain.planet

import java.util.*

object PlanetFactory {
    /**
     * @return a new plant without an owner.
     */
    fun new(): Planet {
        //TODO Generate random names
        return Planet(UUID.randomUUID(), "Planet", null)
    }
}
