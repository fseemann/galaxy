package com.manic.galaxy.context

import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.UserService
import com.manic.galaxy.domain.user.User
import kotlinx.coroutines.runBlocking
import org.koin.core.component.inject

open class Admin() : Empty() {
    val admin: User = runBlocking {
        val userService by inject<UserService>()
        userService.createAdmin("admin@galaxy.com", "admin")
    }

    fun `when the admin creates a galaxy`() {
        val galaxyService by inject<GalaxyService>()

        runBlocking {
            galaxyService.createGalaxy(admin.id, "Cepheus", 10)
        }
    }
}
