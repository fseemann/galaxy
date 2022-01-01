package com.manic.galaxy.application

import com.manic.galaxy.domain.planet.Planet
import com.manic.galaxy.domain.planet.PlanetRepository
import com.manic.galaxy.domain.user.PasswordEncrypter
import com.manic.galaxy.domain.user.User
import com.manic.galaxy.domain.user.UserFactory
import com.manic.galaxy.domain.user.UserRepository
import java.util.*

class UserService(
    private val userRepository: UserRepository,
    private val planetRepository: PlanetRepository,
    private val passwordEncrypter: PasswordEncrypter
) {

    /**
     * @return a admin user
     */
    fun createAdmin(email: String, password: String): User {
        val encryptPassword = passwordEncrypter.encrypt(password)
        val user = UserFactory.newAdmin(email, encryptPassword)
        return userRepository.insert(user)
    }

    /**
     * Assigns an open planet to the user.
     *
     * @return the user's newly owned planet
     * @throws com.manic.galaxy.domain.shared.BusinessException if player has already joined the galaxy before
     * @throws com.manic.galaxy.domain.shared.BusinessException if the galaxy has no own-able planets anymore.
     */
    fun joinGalaxy(
        userId: UUID,
        galaxyId: UUID
    ): Planet {
        val user = userRepository.get(userId)
        val planet = planetRepository.getUnowned(galaxyId)
        planet.transferOwnershipTo(user)
        return planetRepository.update(planet)
    }
}
