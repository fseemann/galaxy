package com.manic.galaxy

import com.manic.galaxy.infrastructure.koin.ModuleFactory
import com.manic.galaxy.infrastructure.postgres.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.slf4j.LoggerFactory
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

abstract class IntegrationTest : KoinComponent {

    @Before
    fun cleanDatabase() {
        transaction {
            tables.forEach { it.deleteAll() }
        }
    }

    companion object {
        val tables = arrayOf(UsersTable, StoragesTable, FacilitiesTable, GalaxiesTable, MinesTable, PlanetsTable)
        private val initialized: Boolean

        init {
            checkInitialized()
            initialized = true

            val postgres = GenericContainer(DockerImageName.parse("postgres:14.1"))
                .withEnv("POSTGRES_PASSWORD", "postgres")
                .withEnv("POSTGRES_DB", "galaxy")
                .withExposedPorts(5432)
            postgres.start()
            Runtime.getRuntime().addShutdownHook(Thread {
                val logger = LoggerFactory.getLogger("Shutdown")
                logger.info("Stopping postgres.")
                postgres.stop()
            })

            val host = postgres.host
            val port = postgres.firstMappedPort
            Database.connect("jdbc:postgresql://${host}:${port}/galaxy", driver = "org.postgresql.Driver",
                             user = "postgres", password = "postgres")

            transaction {
                SchemaUtils.create(tables = tables, inBatch = true)
            }

            startKoin {
                // Koin is not compatible with kotlin 1.6.10, it logs time in info mode which causes an error
                printLogger(Level.ERROR)
                modules(ModuleFactory.new())
            }
        }

        private fun checkInitialized() {
            if (initialized) {
                return
            }
        }
    }
}
