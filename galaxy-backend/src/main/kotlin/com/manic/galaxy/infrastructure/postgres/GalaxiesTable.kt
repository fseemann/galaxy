package com.manic.galaxy.infrastructure.postgres

object GalaxiesTable : EntityTable("galaxies") {
    override val id = uuid("id")
    val name = varchar("name", 255)
}
