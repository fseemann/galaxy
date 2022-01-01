package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.planet.Planet
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class PostgresPlanetsRepository : PostgresEntityRepository<Planet, PlanetsTable>(PlanetsTable) {
    override fun PlanetsTable.fromRow(row: ResultRow): Planet {
        return Planet(
            row[id],
            row[galaxyId],
            row[ownerId],
            row[name]
        )
    }

    override fun PlanetsTable.toRow(
        statement: UpdateBuilder<Number>,
        entity: Planet,
    ) {
        statement[id] = entity.id
        statement[galaxyId] = entity.galaxyId
        statement[ownerId] = entity.ownerId
        statement[name] = entity.name
    }
}
