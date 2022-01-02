package com.manic.galaxy.infrastructure.postgres

object PlanetsTable : EntityTable("planets") {
    override val id = uuid("id")
    val galaxyId = uuid("galaxy_id")
    var ownerId = uuid("owner_id").nullable()
    var name = varchar("name", 255)
    val byOwnerAndGalaxy = index(false, ownerId, galaxyId)
}
