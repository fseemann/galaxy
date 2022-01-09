package com.manic.galaxy.infrastructure.ktor.transactions

import io.ktor.application.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class Transactions(private val configuration: Configuration) {

    fun filter(call: ApplicationCall): Boolean {
        return configuration.filters.any { it(call) }
    }

    class Configuration() {
        internal var database: Database? = null
        internal val filters = mutableListOf<(ApplicationCall) -> Boolean>()

        fun filter(function: (ApplicationCall) -> Boolean) {
            filters.add(function)
        }
    }

    companion object Feature :
        ApplicationFeature<ApplicationCallPipeline, Configuration, Transactions> {
        override val key = AttributeKey<Transactions>("transactions")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: Configuration.() -> Unit,
        ): Transactions {
            val configuration = Configuration().apply(configure)
            val transactions = Transactions(configuration)

            pipeline.receivePipeline.insertPhaseAfter(ApplicationCallPipeline.Features,
                                                      TransactionsPipeline.StartTransaction)

            pipeline.intercept(TransactionsPipeline.StartTransaction) {
                if (!transactions.filter(call)) {
                    return@intercept
                }

                newSuspendedTransaction {
                    proceed()
                }
            }

            return transactions
        }
    }
}
