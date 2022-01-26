package com.manic.galaxy.application

import com.manic.galaxy.IntegrationTest
import com.manic.galaxy.context.Galaxy
import com.manic.galaxy.domain.planet.Mine
import com.manic.galaxy.domain.planet.Storage
import com.manic.galaxy.domain.shared.BusinessException
import com.manic.galaxy.given
import org.junit.Test

class GalaxyJoiningTest : IntegrationTest() {
    @Test
    fun `joining a galaxy should work`() {
        given(::Galaxy) {
            `when a user joins the galaxy`(admin.id)
        }
    }

    @Test(expected = BusinessException::class)
    fun `joining a galaxy twice should fail`() {
        given(::Galaxy) {
            `when a user joins the galaxy`(admin.id)
            `when a user joins the galaxy`(admin.id)
        }
    }

    @Test
    fun `joining a galaxy should give the player a planet with facilities`() {
        given(::Galaxy) {
            `when a user joins the galaxy`(admin.id)
            `then a user should own a single planet with facilities`(
                admin.id,
                listOf(Storage::class, Mine::class)
            )
        }
    }
}
