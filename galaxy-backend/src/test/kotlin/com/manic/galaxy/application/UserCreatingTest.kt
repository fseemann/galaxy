package com.manic.galaxy.application

import com.manic.galaxy.IntegrationTest
import com.manic.galaxy.domain.shared.BusinessException
import com.manic.galaxy.given
import org.junit.Test

class UserCreatingTest : IntegrationTest() {

    @Test
    fun `creating an admin should work`() {
        given {
            `when the system creates an admin`()
        }
    }

    @Test(expected = BusinessException::class)
    fun `creating a user with a used email should fail `() {
        given {
            `when the system creates an admin`()
            `when the system creates an admin`()
        }
    }
}
