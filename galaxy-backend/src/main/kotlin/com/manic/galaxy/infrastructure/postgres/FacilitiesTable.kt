package com.manic.galaxy.infrastructure.postgres

import org.jetbrains.exposed.sql.javatime.timestamp

object FacilitiesTable : EntityTable("facilities") {
    override val id = uuid("id")
    val planetId = uuid("planetId").index()
    val createdAt = timestamp("created_at")
    val _type = varchar("_type", 63)
    val byPlanetAndType = index(false, planetId, _type)
}
