package com.manic.galaxy.infrastructure.koin

import com.manic.galaxy.application.GalaxyService
import com.manic.galaxy.application.PlanetService
import com.manic.galaxy.application.UserService
import com.manic.galaxy.domain.galaxy.GalaxyRepository
import com.manic.galaxy.domain.planet.PlanetRepository
import com.manic.galaxy.domain.user.PasswordEncrypter
import com.manic.galaxy.domain.user.UserRepository
import com.manic.galaxy.infrastructure.bcrypt.BcrycptPasswordEncrypter
import com.manic.galaxy.infrastructure.mongodb.MongoDbGalaxyRepository
import com.manic.galaxy.infrastructure.mongodb.MongoDbPlanetRepository
import com.manic.galaxy.infrastructure.mongodb.MongoDbUserRepository
import com.mongodb.reactivestreams.client.MongoDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

object ModuleFactory {
    fun new(database: MongoDatabase): Module {
        return module(createdAtStart = true) {
            single<UserRepository> { MongoDbUserRepository(database) }
            single<GalaxyRepository> { MongoDbGalaxyRepository(database) }
            single<PlanetRepository> { MongoDbPlanetRepository(database) }
            single<PasswordEncrypter> { BcrycptPasswordEncrypter() }
            single { GalaxyService(get(), get(), get()) }
            single { UserService(get(), get()) }
            single { PlanetService(get()) }
        }
    }
}
