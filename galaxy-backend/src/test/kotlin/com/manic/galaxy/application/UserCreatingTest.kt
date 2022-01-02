package com.manic.galaxy.application

import com.manic.galaxy.IntegrationTest
import com.manic.galaxy.Nothing
import com.manic.galaxy.given
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Test

class UserCreatingTest : IntegrationTest() {

    @Test
    fun `creating an admin should work`() {
        given {
            `when the system creates an admin`()
        }
    }
}

private fun Nothing.`when the system creates an admin`() {
    val userService = IntegrationTest.koin.get<UserService>()

    transaction {
        userService.createAdmin("admin@manic.com", "admin")
    }
}
