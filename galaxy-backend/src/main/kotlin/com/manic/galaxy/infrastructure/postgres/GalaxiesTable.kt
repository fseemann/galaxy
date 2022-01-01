package com.manic.galaxy.infrastructure.postgres

object GalaxiesTable : EntityTable("galaxies") {
    val name = varchar("name", 255)
}
