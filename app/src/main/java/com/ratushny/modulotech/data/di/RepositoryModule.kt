package com.ratushny.modulotech.data.di

import com.ratushny.modulotech.data.repository.DeviceRepository
import com.ratushny.modulotech.data.repository.ModuloRepository
import com.ratushny.modulotech.data.repository.UserRepository
import com.ratushny.modulotech.domain.api.DeviceRepositoryApi
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi
import com.ratushny.modulotech.domain.api.UserRepositoryApi
import org.koin.dsl.module

val repositoryModule = module {
    factory<ModuloRepositoryApi> {
        ModuloRepository(
            service = get(),
            deviceDao = get(),
            userDao = get(),
        )
    }
    factory<DeviceRepositoryApi> {
        DeviceRepository(
            deviceDao = get()
        )
    }
    factory<UserRepositoryApi> {
        UserRepository(
            userDao = get()
        )
    }
}