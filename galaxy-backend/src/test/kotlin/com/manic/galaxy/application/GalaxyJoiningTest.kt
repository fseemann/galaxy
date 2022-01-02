package com.manic.galaxy.application

import com.manic.galaxy.IntegrationTest
import com.manic.galaxy.context.Galaxy
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
}
