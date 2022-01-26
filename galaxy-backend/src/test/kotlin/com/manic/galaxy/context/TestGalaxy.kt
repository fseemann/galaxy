package com.manic.galaxy.context

import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.PlanetService
import com.manic.galaxy.domain.planet.Storage
import kotlinx.coroutines.runBlocking
import org.koin.core.component.inject
import java.util.*
import kotlin.test.assertEquals

class TestGalaxy : Galaxy() {
    val planetOwnedByAdmin = run {
        val galaxyService by inject<GalaxyService>()

        runBlocking {
            galaxyService.joinGalaxy(admin.id, galaxy.id)
        }
    }

    fun `when the planet's storage is updated`(planetId: UUID) {
        val planetService: PlanetService by inject()

        runBlocking {
            planetService.updateStorage(planetId)
        }
    }

    fun `then the planet should have n minerals stored`(
        planetId: UUID,
        n: Int,
    ) {
        val planetService: PlanetService by inject()

        val storage = runBlocking {
            planetService.get(planetId).facilities.first { it is Storage } as Storage
        }

        assertEquals(n, storage.minerals)
    }
}
