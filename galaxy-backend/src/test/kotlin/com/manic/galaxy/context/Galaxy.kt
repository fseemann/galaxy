package com.manic.galaxy.context

import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.UserService
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.inject
import java.util.*

class Galaxy : Admin() {
    val galaxy = run {
        val galaxyService by inject<GalaxyService>()

        transaction {
            galaxyService.createGalaxy(admin.id, "Cepheus", 10)
        }
    }

    fun `when a user joins the galaxy`(userId: UUID) {
        val userService by inject<UserService>()

        transaction {
            userService.joinGalaxy(userId, galaxy.id)
        }
    }
}
