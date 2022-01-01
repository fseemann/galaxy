package com.manic.galaxy.infrastructure.postgres

import org.jetbrains.exposed.sql.Table

open class EntityTable(name: String) : Table(name) {
    val id = uuid("id")
    override val primaryKey: PrimaryKey = PrimaryKey(UsersTable.id)
}
