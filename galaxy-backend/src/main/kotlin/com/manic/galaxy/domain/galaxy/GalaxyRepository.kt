package com.manic.galaxy.domain.galaxy

import com.manic.galaxy.domain.shared.EntityRepository

interface GalaxyRepository : EntityRepository<Galaxy> {
    fun list(): List<Galaxy>
}
