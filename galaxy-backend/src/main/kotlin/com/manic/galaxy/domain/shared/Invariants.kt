package com.manic.galaxy.domain.shared

object Invariants {
    fun require(precondition: Boolean, message: () -> String) {
        if (!precondition) {
            throw BusinessException(message())
        }
    }
}
