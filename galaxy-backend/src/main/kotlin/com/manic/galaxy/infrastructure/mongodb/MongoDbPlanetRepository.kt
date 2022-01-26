package com.manic.galaxy.infrastructure.mongodb

import com.manic.galaxy.domain.planet.Planet
import com.manic.galaxy.domain.planet.PlanetRepository
import com.manic.galaxy.domain.shared.BusinessException
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import java.util.*

class MongoDbPlanetRepository(database: MongoDatabase) : MongoDbEntityRepository<Planet>(), PlanetRepository {
    override val collection: MongoCollection<Planet> = database.getCollection("planet", Planet::class.java)

    override suspend fun insert(planets: List<Planet>) {
        collection.insertMany(planets).toSingle()
    }

    override suspend fun getUnowned(galaxyId: UUID): Planet {
        return collection.find(Filters.and(
            Filters.eq("galaxyId", galaxyId),
            Filters.eq("ownerId", null)
        )).first().toSingle()
    }

    override suspend fun requireNotOwner(userId: UUID) {
        val count = collection.countDocuments(Filters.eq("ownerId", userId)).toSingle()

        if (count > 0) {
            throw BusinessException("user.alreadyOwner")
        }
    }

    override suspend fun list(galaxyId: UUID, userId: UUID): List<Planet> {
        return collection.find(Filters.and(
            Filters.eq("galaxyId", galaxyId),
            Filters.eq("ownerId", userId)
        )).toMultiple()
    }
}