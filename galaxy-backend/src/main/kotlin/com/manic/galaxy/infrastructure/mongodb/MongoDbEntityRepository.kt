package com.manic.galaxy.infrastructure.mongodb

import com.manic.galaxy.domain.shared.Entity
import com.manic.galaxy.domain.shared.EntityRepository
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoCollection
import java.util.*

abstract class MongoDbEntityRepository<T : Entity> : EntityRepository<T> {
    protected abstract val collection: MongoCollection<T>

    override suspend fun insert(entity: T): T {
        collection.insertOne(entity).toSingle()
        return entity
    }

    override suspend fun update(entity: T): T {
        collection.replaceOne(Filters.eq("_id", entity.id), entity).toSingle()
        return entity
    }

    override suspend fun delete(entity: T) {
        collection.deleteOne(Filters.eq("_id", entity.id)).toSingle()
    }

    override suspend fun get(id: UUID): T {
        return collection.find(Filters.eq("_id", id)).toSingle()
    }
}