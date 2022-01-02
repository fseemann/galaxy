package com.manic.galaxy.context

import com.manic.galaxy.application.FacilityService
import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.UserService
import com.manic.galaxy.domain.facility.Facility
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.inject
import java.util.*
import kotlin.reflect.KClass
import kotlin.test.assertEquals

class Galaxy : Admin() {
    val galaxy = run {
        val galaxyService by inject<GalaxyService>()

        transaction {
            galaxyService.createGalaxy(admin.id, "Cepheus", 10)
        }
    }

    fun `when a user joins the galaxy`(userId: UUID) {
        val userService by inject<UserService>()

        transaction {
            userService.joinGalaxy(userId, galaxy.id)
        }
    }

    fun `then a user should own a single planet with facilities`(
        userId: UUID,
        facilityTypes: List<KClass<out Facility>>,
    ) {
        val facilityService by inject<FacilityService>()
        val userService by inject<UserService>()

        val facilities = transaction {
            val planet = userService.listPlanets(userId, galaxy.id).first()
            facilityService.listFacilities(planet.id)
        }

        assertEquals(facilityTypes.sortedBy { it.simpleName }, facilities.map { it::class }.sortedBy { it.simpleName })
    }
}
