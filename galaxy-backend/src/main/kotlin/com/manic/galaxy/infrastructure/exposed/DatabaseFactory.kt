package com.manic.galaxy.infrastructure.exposed

import com.manic.galaxy.infrastructure.postgres.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    val tables = arrayOf(UsersTable, StoragesTable, FacilitiesTable, GalaxiesTable, MinesTable, PlanetsTable)

    fun new(
        host: String,
        port: Int,
        username: String,
        password: String,
        name: String,
    ): Database {
        val database = Database.connect("jdbc:postgresql://${host}:${port}/${name}", driver = "org.postgresql.Driver",
                                        user = username, password = password)

        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(
                tables = tables,
                inBatch = true
            )
        }

        return database
    }
}
