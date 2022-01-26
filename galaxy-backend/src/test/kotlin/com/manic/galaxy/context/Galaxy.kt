package com.manic.galaxy.context

import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.PlanetService
import com.manic.galaxy.domain.planet.Facility
import kotlinx.coroutines.runBlocking
import org.koin.core.component.inject
import java.util.*
import kotlin.reflect.KClass
import kotlin.test.assertEquals

open class Galaxy : Admin() {
    val galaxy = run {
        val galaxyService by inject<GalaxyService>()

        runBlocking {
            galaxyService.createGalaxy(admin.id, "Cepheus", 10)
        }
    }

    fun `when a user joins the galaxy`(userId: UUID) {
        val galaxyService by inject<GalaxyService>()

        runBlocking {
            galaxyService.joinGalaxy(userId, galaxy.id)
        }
    }

    fun `then a user should own a single planet with facilities`(
        userId: UUID,
        facilityTypes: List<KClass<out Facility>>,
    ) {
        val planetService by inject<PlanetService>()

        val facilities = runBlocking {
            val planet = planetService.listPlanets(userId, galaxy.id).first()
            planet.facilities
        }

        assertEquals(facilityTypes.sortedBy { it.simpleName }, facilities.map { it::class }.sortedBy { it.simpleName })
    }
}
