package com.manic.galaxy.domain.user

import com.manic.galaxy.domain.shared.BsonDocument
import com.manic.galaxy.domain.shared.Entity
import java.util.*

@BsonDocument
class User(
    override val id: UUID,
    val email: String,
    val password: String,
    val role: UserRole
): Entity
