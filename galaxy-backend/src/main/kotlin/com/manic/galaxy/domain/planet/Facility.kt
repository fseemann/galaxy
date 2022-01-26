package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.shared.BsonDocument
import com.manic.galaxy.domain.shared.Entity
import org.bson.codecs.pojo.annotations.BsonDiscriminator
import java.time.Instant
import java.util.*

@BsonDocument
@BsonDiscriminator
abstract class Facility : Entity {
    abstract val planetId: UUID
    abstract val createdAt: Instant
}
