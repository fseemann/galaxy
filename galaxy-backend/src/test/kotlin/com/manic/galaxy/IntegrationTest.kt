package com.manic.galaxy

import com.manic.galaxy.infrastructure.koin.ModuleFactory
import com.manic.galaxy.infrastructure.postgres.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

abstract class IntegrationTest : KoinComponent {

    companion object {
        val koin: Koin

        init {
            val postgres = GenericContainer(DockerImageName.parse("postgres:14.1"))
                .withEnv("POSTGRES_PASSWORD", "postgres")
                .withEnv("POSTGRES_DB", "galaxy")
                .withExposedPorts(5432)
            postgres.start()

            val host = postgres.host
            val port = postgres.firstMappedPort

            Database.connect("jdbc:postgresql://${host}:${port}/galaxy", driver = "org.postgresql.Driver",
                             user = "postgres", password = "postgres")

            transaction {
                SchemaUtils.create(UsersTable, StoragesTable, FacilitiesTable, GalaxiesTable, MinesTable, PlanetsTable)
            }

            koin = startKoin {
                printLogger(Level.ERROR)
                modules(ModuleFactory.new())
            }.koin
        }
    }
}
