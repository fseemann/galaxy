package com.manic.galaxy.infrastructure.koin

import com.manic.galaxy.application.FacilityService
import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.UserService
import com.manic.galaxy.domain.facility.FacilityRepository
import com.manic.galaxy.domain.galaxy.GalaxyRepository
import com.manic.galaxy.domain.planet.PlanetRepository
import com.manic.galaxy.domain.user.PasswordEncrypter
import com.manic.galaxy.domain.user.UserRepository
import com.manic.galaxy.infrastructure.bcrypt.BcrycptPasswordEncrypter
import com.manic.galaxy.infrastructure.postgres.PostgresFacilityRepository
import com.manic.galaxy.infrastructure.postgres.PostgresGalaxyRepository
import com.manic.galaxy.infrastructure.postgres.PostgresPlanetRepository
import com.manic.galaxy.infrastructure.postgres.PostgresUserRepository
import org.koin.core.module.Module
import org.koin.dsl.module

object ModuleFactory {
    fun new(): Module {
        return module {
            single<UserRepository> { PostgresUserRepository() }
            single<GalaxyRepository> { PostgresGalaxyRepository() }
            single<PlanetRepository> { PostgresPlanetRepository() }
            single<FacilityRepository> { PostgresFacilityRepository() }
            single<PasswordEncrypter> { BcrycptPasswordEncrypter() }
            single { FacilityService(get()) }
            single { GalaxyService(get(), get(), get(), get()) }
            single { UserService(get(), get()) }
        }
    }
}
