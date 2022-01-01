package com.manic.galaxy.domain.shared

import java.util.*

interface EntityRepository<T> {
    fun insert(entity: T): T
    fun update(entity: T): T
    fun delete(entity: T)
    fun get(id: UUID): T
}
