package com.ratushny.modulotech.domain.di

import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.interactor.ModuloInteractor
import com.ratushny.modulotech.domain.interactor.UserInteractor
import org.koin.dsl.module

val interactorModule = module {
    factory { ModuloInteractor(repository = get()) }
    factory { DeviceInteractor(repository = get()) }
    factory { UserInteractor(repository = get()) }
}