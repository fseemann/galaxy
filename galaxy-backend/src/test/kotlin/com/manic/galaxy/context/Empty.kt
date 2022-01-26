package com.manic.galaxy.context

import com.manic.galaxy.application.UserService
import com.manic.galaxy.domain.shared.GalaxyTime
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Duration

open class Empty() : KoinComponent {
    fun `when the system creates an admin`(
        email: String = "admin@galaxy.com",
        password: String = "admin",
    ) {
        val userService by inject<UserService>()

        runBlocking {
            userService.createAdmin(email, password)
        }
    }

    fun `when a user signs in`(
        email: String,
        password: String,
    ) {
        val userService by inject<UserService>()

        runBlocking {
            userService.signIn(email, password)
        }
    }

    fun `when time passes`(duration: Duration) {
        GalaxyTime.time = GalaxyTime.now().plus(duration)
    }
}

