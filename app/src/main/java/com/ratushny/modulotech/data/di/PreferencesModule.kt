package com.ratushny.modulotech.data.di

import com.ratushny.modulotech.data.preferences.PreferencesEntry
import org.koin.dsl.module

val preferencesModule = module {
    single { PreferencesEntry(sharedPreferences = get()) }
}