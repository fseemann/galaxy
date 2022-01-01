package com.manic.galaxy.infrastructure.postgres

object MinesTable : EntityTable("mines") {
    override val id = reference("id", FacilitiesTable.id)
    val mineralsPerMinute = integer("minerals_per_minute")
}
