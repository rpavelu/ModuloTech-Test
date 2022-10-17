package com.ratushny.modulotech.presentation

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.ratushny.modulotech.BuildConfig
import com.ratushny.modulotech.data.di.preferencesModule
import com.ratushny.modulotech.data.network.networkModule
import com.ratushny.modulotech.domain.di.domainInteractorModule
import com.ratushny.modulotech.domain.di.domainRepositoryModule
import com.ratushny.modulotech.presentation.di.*
import com.ratushny.modulotech.presentation.screen.settings.SettingsFragment
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber

class Application : Application() {

    private val sharedPreferences by lazy { getSharedPreferences(packageName, MODE_PRIVATE) }

    private val sharedPreferencesModule = module {
        single<SharedPreferences> { sharedPreferences }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        initKoin()
        initDarkMode()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@Application)
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            modules(
                sharedPreferencesModule,
                deviceListFragmentModule,
                domainInteractorModule,
                domainRepositoryModule,
                networkModule,
                preferencesModule,
                settingsFragmentModule,
                lightFragmentModule,
                heaterFragmentModule,
                rollerShutterFragmentModule,
            )
        }
    }

    private fun initDarkMode() {
        if (sharedPreferences != null && sharedPreferences.getBoolean(
                SettingsFragment.DARK_THEME_PREF_KEY, false
            )
        ) {
            Timber.i("Setting dark theme on startup")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            Timber.i("Setting light theme on startup")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}