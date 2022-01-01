package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.shared.Entity
import com.manic.galaxy.domain.user.User
import java.util.*

class Planet(
    override val id: UUID,
    val galaxyId: UUID,
    var ownerId: UUID?,
    var name: String
): Entity {
    fun transferOwnershipTo(user: User) {
        ownerId = user.id
    }
}
