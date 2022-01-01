package com.manic.galaxy.domain.galaxy

import com.manic.galaxy.domain.shared.Entity
import java.util.*

class Galaxy(
    override val id: UUID,
    val name: String,
): Entity
