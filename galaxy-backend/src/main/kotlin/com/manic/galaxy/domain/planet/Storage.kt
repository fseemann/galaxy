package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.shared.GalaxyTime
import java.time.Duration
import java.time.Instant
import java.util.*

class Storage(
    override val id: UUID,
    override val planetId: UUID,
    override val createdAt: Instant,
    var minerals: Int,
    var mineralsCapacity: Int,
    var lastUpdatedAt: Instant,
) : Facility() {

    fun update(mine: Mine?) {
        val updatedAt = GalaxyTime.now()

        if (mine != null) {
            val passedTimeSinceLastUpdate = Duration.between(
                if (mine.createdAt.isAfter(lastUpdatedAt)) mine.createdAt else lastUpdatedAt,
                updatedAt
            )
            val minedMinerals = mine.mineralsPerMinute * passedTimeSinceLastUpdate.toMinutes().toInt()
            minerals = (minerals + minedMinerals).coerceAtMost(mineralsCapacity)
        }

        lastUpdatedAt = updatedAt
    }
}
