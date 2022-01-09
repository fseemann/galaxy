package com.manic.galaxy.infrastructure.ktor.transactions

import io.ktor.util.pipeline.*

object TransactionsPipeline {
    val StartTransaction = PipelinePhase("transaction")
}
