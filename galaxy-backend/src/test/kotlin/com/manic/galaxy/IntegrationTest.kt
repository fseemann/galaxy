package com.manic.galaxy

import com.manic.galaxy.domain.shared.GalaxyTime
import com.manic.galaxy.infrastructure.exposed.DatabaseFactory
import com.manic.galaxy.infrastructure.koin.ModuleFactory
import com.manic.galaxy.infrastructure.postgres.FacilitiesTable
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
            DatabaseFactory.tables.filterNot { it is FacilitiesTable }.forEach { it.deleteAll() }
        }
    }

    @Before
    fun resetTime() {
        GalaxyTime.time = null
    }

    companion object {
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
            DatabaseFactory.new(host, port, "postgres", "postgres", "galaxy")

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
