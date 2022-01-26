package com.manic.galaxy

import com.manic.galaxy.domain.shared.GalaxyTime
import com.manic.galaxy.infrastructure.koin.ModuleFactory
import com.manic.galaxy.infrastructure.mongodb.DatabaseFactory
import com.manic.galaxy.infrastructure.mongodb.toMultiple
import com.manic.galaxy.infrastructure.mongodb.toSingle
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.slf4j.LoggerFactory
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

abstract class IntegrationTest : KoinComponent {

    @Before
    fun cleanDatabase() = runBlocking<Unit> {
        mongoDatabase.listCollectionNames().toMultiple().forEach {
            mongoDatabase.getCollection(it).deleteMany(Filters.exists("_id")).toSingle()
        }
    }

    @Before
    fun resetTime() {
        GalaxyTime.time = null
    }

    companion object {
        private val initialized: Boolean
        private val mongoDatabase: MongoDatabase

        init {
            checkInitialized()
            initialized = true

            val mongo = GenericContainer(DockerImageName.parse("mongo:5"))
                .withEnv("MONGO_INITDB_ROOT_USERNAME", "root")
                .withEnv("MONGO_INITDB_ROOT_PASSWORD", "root")
                .withExposedPorts(27017)
            mongo.start()
            Runtime.getRuntime().addShutdownHook(Thread {
                val logger = LoggerFactory.getLogger("Shutdown")
                logger.info("Stopping mongodb.")
                mongo.stop()
            })

            mongoDatabase = DatabaseFactory.new("mongodb://root:root@${mongo.host}:${mongo.firstMappedPort}/galaxy")

            startKoin {
                // Koin is not compatible with kotlin 1.6.10, it logs time in info mode which causes an error
                printLogger(Level.ERROR)
                modules(ModuleFactory.new(mongoDatabase))
            }
        }

        private fun checkInitialized() {
            if (initialized) {
                return
            }
        }
    }
}
