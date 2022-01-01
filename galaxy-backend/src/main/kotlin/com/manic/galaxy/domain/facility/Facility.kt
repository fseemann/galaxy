package com.manic.galaxy.domain.facility

import com.manic.galaxy.domain.shared.Entity
import java.time.Instant
import java.util.*

interface Facility : Entity {
    val planetId: UUID
    val createdAt: Instant
}
