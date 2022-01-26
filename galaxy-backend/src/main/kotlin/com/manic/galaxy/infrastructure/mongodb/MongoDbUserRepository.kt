package com.manic.galaxy.infrastructure.mongodb

import com.manic.galaxy.domain.shared.BusinessException
import com.manic.galaxy.domain.user.User
import com.manic.galaxy.domain.user.UserRepository
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoDatabase


class MongoDbUserRepository(database: MongoDatabase) : MongoDbEntityRepository<User>(), UserRepository {
    override val collection = database.getCollection("user", User::class.java)

    override suspend fun requireUnique(email: String) {
        val count = collection.countDocuments(Filters.eq("email", email)).toSingle()

        if (count > 0L) {
            throw BusinessException("user.nameTaken")
        }
    }

    override suspend fun getByEmail(email: String): User {
        return collection.find(Filters.eq("email", email)).toSingleOrNull() ?: throw BusinessException("user.notFound")
    }
}