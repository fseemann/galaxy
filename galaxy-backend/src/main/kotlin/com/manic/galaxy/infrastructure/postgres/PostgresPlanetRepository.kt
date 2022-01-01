package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.planet.Planet
import com.manic.galaxy.domain.planet.PlanetRepository
import com.manic.galaxy.domain.shared.BusinessException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.util.*

class PostgresPlanetRepository : PostgresEntityRepository<Planet, PlanetsTable>(PlanetsTable), PlanetRepository {

    override fun insert(planets: List<Planet>) {
        planets.forEach(::insert)
    }

    override fun getUnowned(galaxyId: UUID): Planet {
        return PlanetsTable.select { PlanetsTable.galaxyId eq galaxyId and (PlanetsTable.ownerId eq null) }
            .limit(1)
            .map { PlanetsTable.fromRow(it) }
            .firstOrNull()
            ?: throw BusinessException("galaxy.noUnownedPlanetLeft")
    }

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
