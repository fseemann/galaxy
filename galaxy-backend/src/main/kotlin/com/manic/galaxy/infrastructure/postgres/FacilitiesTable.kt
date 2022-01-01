package com.manic.galaxy.infrastructure.postgres

import org.jetbrains.exposed.sql.javatime.timestamp

object FacilitiesTable : EntityTable("facilities") {
    val planetId = uuid("planetId")
    val createdAt = timestamp("created_at")
    val _type = varchar("_type", 63)
}
