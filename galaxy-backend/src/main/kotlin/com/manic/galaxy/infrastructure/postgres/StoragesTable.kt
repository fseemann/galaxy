package com.manic.galaxy.infrastructure.postgres

import org.jetbrains.exposed.sql.javatime.timestamp

object StoragesTable : EntityTable("storages") {
    override val id = uuid("id")
    var minerals = integer("minerals")
    var mineralsCapacity = integer("minerals_capacity")
    var lastUpdatedAt = timestamp("last_updated_at")
}
