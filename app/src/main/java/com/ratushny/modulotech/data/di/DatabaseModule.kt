package com.ratushny.modulotech.data.di

import com.ratushny.modulotech.data.database.DatabaseProvider
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseProvider(context = get()) }
    single { get<DatabaseProvider>().deviceDao }
    single { get<DatabaseProvider>().userDao }
}