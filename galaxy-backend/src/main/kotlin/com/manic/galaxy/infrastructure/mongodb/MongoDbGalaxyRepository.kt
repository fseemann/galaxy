package com.manic.galaxy.infrastructure.mongodb

import com.manic.galaxy.domain.galaxy.Galaxy
import com.manic.galaxy.domain.galaxy.GalaxyRepository
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase

class MongoDbGalaxyRepository(database: MongoDatabase): MongoDbEntityRepository<Galaxy>(), GalaxyRepository {
    override val collection: MongoCollection<Galaxy> = database.getCollection("galaxy", Galaxy::class.java)

    override suspend fun list(): List<Galaxy> {
        return collection.find().toMultiple()
    }
}