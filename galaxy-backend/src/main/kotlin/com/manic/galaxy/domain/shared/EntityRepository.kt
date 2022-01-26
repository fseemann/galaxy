package com.manic.galaxy.domain.shared

import java.util.*

interface EntityRepository<T> {
    suspend fun insert(entity: T): T
    suspend fun update(entity: T): T
    suspend fun delete(entity: T)
    suspend fun get(id: UUID): T
}
