package com.manic.galaxy.domain.galaxy

import com.manic.galaxy.domain.shared.BsonDocument
import com.manic.galaxy.domain.shared.Entity
import java.util.*

@BsonDocument
class Galaxy(
    override val id: UUID,
    val name: String,
): Entity
