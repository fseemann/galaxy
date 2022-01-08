package com.manic.galaxy.application

import com.manic.galaxy.domain.user.PasswordEncrypter
import com.manic.galaxy.domain.user.User
import com.manic.galaxy.domain.user.UserFactory
import com.manic.galaxy.domain.user.UserRepository

class UserService(
    private val userRepository: UserRepository,
    private val passwordEncrypter: PasswordEncrypter,
) {

    /**
     * @return a admin user
     */
    fun createAdmin(
        email: String,
        password: String,
    ): User {
        userRepository.requireUnique(email)
        val encryptPassword = passwordEncrypter.encrypt(password)
        val user = UserFactory.newAdmin(email, encryptPassword)
        return userRepository.insert(user)
    }

    fun signIn(
        email: String,
        password: String,
    ): User {
        val user = userRepository.getByEmail(email)
        passwordEncrypter.validate(password, user.password)
        return user
    }
}
