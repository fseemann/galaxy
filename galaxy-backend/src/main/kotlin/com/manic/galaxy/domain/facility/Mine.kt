package com.manic.galaxy.domain.facility

import java.time.Instant
import java.util.*

class Mine(
    override val id: UUID,
    override val planetId: UUID,
    override val createdAt: Instant,
    var mineralsPerMinute: Int,
) : Facility
