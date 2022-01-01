package com.manic.galaxy.domain.facility

import com.manic.galaxy.domain.shared.EntityRepository
import java.util.*

interface FacilityRepository : EntityRepository<Facility> {
    fun getStorage(id: UUID): Storage
    fun findMine(planetId: UUID): Mine?
    fun insert(vararg facilities: Facility): List<Facility>
}
