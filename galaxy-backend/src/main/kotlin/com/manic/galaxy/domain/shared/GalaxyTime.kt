package com.manic.galaxy.domain.shared

import java.time.Instant

object GalaxyTime {
    var time: Instant? = null

    fun now(): Instant = time ?: Instant.now()
}
