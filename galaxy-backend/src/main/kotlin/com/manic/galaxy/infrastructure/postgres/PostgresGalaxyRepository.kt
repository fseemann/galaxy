package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.galaxy.Galaxy
import com.manic.galaxy.domain.galaxy.GalaxyRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class PostgresGalaxyRepository : PostgresEntityRepository<Galaxy, GalaxiesTable>(GalaxiesTable), GalaxyRepository {
    override fun GalaxiesTable.fromRow(row: ResultRow): Galaxy {
        return Galaxy(
            row[id],
            row[name]
        )
    }

    override fun GalaxiesTable.toRow(
        statement: UpdateBuilder<Number>,
        entity: Galaxy,
    ) {
        statement[id] = entity.id
        statement[name] = entity.name
    }

}

