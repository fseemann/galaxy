package com.manic.galaxy.application

import com.manic.galaxy.IntegrationTest
import com.manic.galaxy.context.Admin
import com.manic.galaxy.context.Galaxy
import com.manic.galaxy.given
import org.junit.Test

class GalaxyCreatingTest : IntegrationTest() {

    @Test
    fun `creating a galaxy should work`() {
        given(::Admin) {
            `when the admin creates a galaxy`()
        }
    }

    @Test
    fun `joining a galaxy should work`() {
        given(::Galaxy) {
            `when a user joins the galaxy`(admin.id)
        }
    }
}
