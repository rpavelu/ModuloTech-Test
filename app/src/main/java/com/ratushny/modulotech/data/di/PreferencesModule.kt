package com.ratushny.modulotech.data.di

import com.ratushny.modulotech.data.repository.preferences.PreferencesRepository
import org.koin.dsl.module

val preferencesModule = module {
    single { PreferencesRepository(context = get()) }
}