package com.manic.galaxy.application

import com.manic.galaxy.domain.facility.Facility
import com.manic.galaxy.domain.facility.FacilityRepository
import java.util.*

class FacilityService(
    private val facilityRepository: FacilityRepository,
) {

    /**
     * Updates the storage based on the mining facilities of the planet.
     *
     * The amount that is stored in the storage depends on how many minutes passed since the last update call.
     */
    fun updateStorage(storageId: UUID) {
        val storage = facilityRepository.getStorage(storageId)
        val mine = facilityRepository.findMine(storage.planetId)
        storage.update(mine)
        facilityRepository.update(storage)
    }

    fun listFacilities(planetId: UUID): List<Facility> {
        return facilityRepository.list(planetId)
    }
}
