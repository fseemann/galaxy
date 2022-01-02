package com.manic.galaxy.infrastructure.postgres

import org.jetbrains.exposed.sql.ReferenceOption

object MinesTable : EntityTable("mines") {
    override val id = reference("id", FacilitiesTable.id, onDelete = ReferenceOption.CASCADE)
    val mineralsPerMinute = integer("minerals_per_minute")
}
