package com.manic.galaxy.infrastructure.postgres

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

abstract class EntityTable(name: String) : Table(name) {
    abstract val id: Column<UUID>
    override val primaryKey: PrimaryKey by lazy { PrimaryKey(id) }
}
