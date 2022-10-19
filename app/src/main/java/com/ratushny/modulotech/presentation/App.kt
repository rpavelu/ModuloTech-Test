package com.ratushny.modulotech.presentation

import android.app.Application
import com.ratushny.modulotech.BuildConfig
import com.ratushny.modulotech.data.di.databaseModule
import com.ratushny.modulotech.data.di.networkModule
import com.ratushny.modulotech.data.di.preferencesModule
import com.ratushny.modulotech.data.di.repositoryModule
import com.ratushny.modulotech.domain.di.interactorModule
import com.ratushny.modulotech.presentation.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        initKoin()
    }

    private val dataLayerModules = module {
        includes(
            databaseModule,
            networkModule,
            preferencesModule,
            repositoryModule,
        )
    }

    private val domainLayerModules = module {
        includes(
            interactorModule,
        )
    }

    private val presentationLayerModules = module {
        includes(
            deviceListFragmentModule,
            userFragmentModule,
            lightFragmentModule,
            heaterFragmentModule,
            rollerShutterFragmentModule,
        )
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            modules(
                dataLayerModules,
                domainLayerModules,
                presentationLayerModules,
            )
        }
    }
}