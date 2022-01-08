package com.manic.galaxy.context

import com.manic.galaxy.application.FacilityService
import com.manic.galaxy.application.GalaxyService
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.inject
import java.util.*
import kotlin.test.assertEquals

class TestGalaxy : Galaxy() {
    val planetOwnedByAdmin = run {
        val galaxyService by inject<GalaxyService>()

        transaction {
            galaxyService.joinGalaxy(admin.id, galaxy.id)
        }
    }

    fun `when the storage of planet is updated`(planetId: UUID) {
        val facilityService: FacilityService by inject()

        transaction {
            facilityService.updateStorage(planetId)
        }
    }

    fun `then the storage of planet should have n minerals`(
        planetId: UUID,
        n: Int,
    ) {
        val facilityService: FacilityService by inject()

        val storage = transaction {
            facilityService.getStorage(planetId)
        }

        assertEquals(n, storage.minerals)
    }
}
