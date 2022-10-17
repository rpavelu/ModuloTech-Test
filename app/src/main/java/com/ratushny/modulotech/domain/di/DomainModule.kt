package com.ratushny.modulotech.domain.di

import com.ratushny.modulotech.data.repository.ModuloRepository
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi
import com.ratushny.modulotech.domain.interactor.ModuloInteractor
import org.koin.dsl.module

val domainRepositoryModule = module {
    single<ModuloRepositoryApi> { ModuloRepository(service = get(), preferencesEntry = get()) }
}

val domainInteractorModule = module {
    single { ModuloInteractor(repository = get()) }
}