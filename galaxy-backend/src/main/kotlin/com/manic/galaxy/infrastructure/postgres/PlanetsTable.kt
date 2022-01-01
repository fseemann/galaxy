package com.manic.galaxy.infrastructure.postgres

object PlanetsTable : EntityTable("planets") {
    val galaxyId = uuid("galaxyId")
    var ownerId = uuid("ownerId").nullable()
    var name = varchar("name", 255)
}
