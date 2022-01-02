package com.manic.galaxy.application

import com.manic.galaxy.IntegrationTest
import com.manic.galaxy.context.TestGalaxy
import com.manic.galaxy.given
import org.junit.Test
import java.time.Duration

class StorageUpdateTest : IntegrationTest() {

    @Test
    fun `updating storage should increase minerals depending on minutes passed`() {
        given(::TestGalaxy) {
            `when time passes`(Duration.ofMinutes(10))
            `when the storage of planet is updated`(planetOwnedByAdmin.id)
            `then the storage of planet should have n minerals`(planetOwnedByAdmin.id, 10)
        }
    }
}
