package com.ratushny.modulotech.domain.di

import com.ratushny.modulotech.data.repository.DeviceRepository
import com.ratushny.modulotech.data.repository.ModuloRepository
import com.ratushny.modulotech.data.repository.UserRepository
import com.ratushny.modulotech.domain.api.DeviceRepositoryApi
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi
import com.ratushny.modulotech.domain.api.UserRepositoryApi
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.interactor.ModuloInteractor
import com.ratushny.modulotech.domain.interactor.UserInteractor
import org.koin.dsl.module

val domainRepositoryModule = module {
    single<ModuloRepositoryApi> {
        ModuloRepository(
            service = get(),
            deviceDao = get(),
            userDao = get(),
            preferencesEntry = get(),
        )
    }
    single<DeviceRepositoryApi> {
        DeviceRepository(
            deviceDao = get(),
            preferencesEntry = get(),
        )
    }
    single<UserRepositoryApi> {
        UserRepository(
            userDao = get()
        )
    }
}

val domainInteractorModule = module {
    single { ModuloInteractor(repository = get()) }
    single { DeviceInteractor(repository = get()) }
    single { UserInteractor(repository = get()) }
}