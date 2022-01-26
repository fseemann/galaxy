package com.manic.galaxy.domain.planet

import com.manic.galaxy.domain.shared.BsonDocument
import com.manic.galaxy.domain.shared.BusinessException
import com.manic.galaxy.domain.shared.Entity
import com.manic.galaxy.domain.user.User
import java.util.*

@BsonDocument
class Planet(
    override val id: UUID,
    val galaxyId: UUID,
    var ownerId: UUID?,
    var name: String,
    var facilities: List<Facility>
): Entity {
    fun transferOwnershipTo(user: User) {
        if (ownerId == null) {
            val mine = FacilityFactory.newMine(id)
            val storage = FacilityFactory.newStorage(id)
            facilities = facilities + listOf(mine, storage)
        }

        ownerId = user.id
    }

    fun updateStorage() {
        val storage = facilities.firstOrNull { it is Storage } as Storage?
            ?: throw BusinessException("planet.storageNotFound")
        val mine = facilities.firstOrNull { it is Mine } as Mine?

        storage.update(mine)
    }
}
