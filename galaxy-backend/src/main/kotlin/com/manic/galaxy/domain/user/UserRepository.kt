package com.manic.galaxy.domain.user

import com.manic.galaxy.domain.shared.EntityRepository

interface UserRepository : EntityRepository<User> {
    suspend fun requireUnique(email: String)
    suspend fun getByEmail(email: String): User
}
