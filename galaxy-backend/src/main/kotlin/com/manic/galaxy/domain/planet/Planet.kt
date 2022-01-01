package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.shared.Entity
import com.manic.galaxy.domain.user.User
import java.util.*

class Planet(
    override val id: UUID,
    var name: String,
    var ownerId: UUID?
): Entity {
    fun transferOwnershipTo(user: User) {
        ownerId = user.id
    }
}
