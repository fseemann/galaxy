package com.manic.galaxy.domain.user

import com.manic.galaxy.domain.shared.EntityRepository

interface UserRepository : EntityRepository<User> {
    fun requireUnique(email: String)
    fun getByEmail(email: String): User
}
