package com.ratushny.modulotech.presentation

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.ratushny.modulotech.BuildConfig
import com.ratushny.modulotech.R
import com.ratushny.modulotech.data.network.networkModule
import com.ratushny.modulotech.domain.di.domainInteractorModule
import com.ratushny.modulotech.domain.di.domainRepositoryModule
import com.ratushny.modulotech.presentation.di.deviceListFragmentModule
import com.ratushny.modulotech.presentation.screen.settings.SettingsFragment
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class Application : Application() {

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
                deviceListFragmentModule,
                domainInteractorModule,
                domainRepositoryModule,
                networkModule,
            )
        }
    }

    private fun initDarkMode() {
        val settingsPrefs =
            getSharedPreferences(getString(R.string.settings_preference), Context.MODE_PRIVATE)
        if (settingsPrefs != null && settingsPrefs.getBoolean(
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