package com.manic.galaxy.infrastructure.postgres

import com.manic.galaxy.domain.shared.BusinessException
import com.manic.galaxy.domain.shared.Entity
import com.manic.galaxy.domain.shared.EntityRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.util.*

abstract class PostgresEntityRepository<T : Entity, J : EntityTable>(private val table: J) : EntityRepository<T> {
    override fun insert(entity: T): T {
        table.insert { toRow(it, entity) }
        return entity
    }

    override fun update(entity: T): T {
        table.update({ table.id eq entity.id }) { toRow(it, entity) }
        return entity
    }

    override fun delete(entity: T) {
        table.deleteWhere { table.id eq entity.id }
    }

    override fun get(id: UUID): T {
        return table.select { table.id eq id }.map { table.fromRow(it) }.firstOrNull()
            ?: throw BusinessException("entity.idUnused")
    }

    protected abstract fun J.fromRow(row: ResultRow): T

    protected abstract fun J.toRow(
        statement: UpdateBuilder<Number>,
        entity: T,
    )
}
