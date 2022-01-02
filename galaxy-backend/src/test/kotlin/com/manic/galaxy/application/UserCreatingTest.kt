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

    @Test
    fun `sign in should work`() {
        given {
            `when the system creates an admin`(email = "test@manic.com", password = "test")
            `when a user signs in`(email = "test@manic.com", password = "test")
        }
    }

    @Test(expected = BusinessException::class)
    fun `sign in with an invalid password should fail`() {
        given {
            `when the system creates an admin`(email = "test@manic.com", password = "test")
            `when a user signs in`(email = "test@manic.com", password = "WRONG")
        }
    }

    @Test(expected = BusinessException::class)
    fun `sign in with an unknown email should fail`() {
        given {
            `when a user signs in`(email = "unknown@email.com", password = "test")
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
