package com.manic.galaxy.infrastructure.koin

import com.manic.galaxy.application.FacilityService
import com.manic.galaxy.application.GalaxyService
import org.koin.core.module.Module
import org.koin.dsl.module

object ModuleFactory {
    fun new(): Module {
        return module {
            single { FacilityService(get()) }
            single { GalaxyService(get(), get(), get()) }
            single { FacilityService(get()) }
        }
    }
}
