package com.ratushny.modulotech.data.repository.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferencesRepository(
    private val context: Context
) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            context.packageName,
            Application.MODE_PRIVATE
        )
    }

    var isDataLoaded by booleanPreference(IS_DATA_LOADED, false)


    private fun booleanPreference(
        key: String,
        default: Boolean
    ): ReadWriteProperty<PreferencesRepository, Boolean> {
        return object : ReadWriteProperty<PreferencesRepository, Boolean> {

            override fun getValue(thisRef: PreferencesRepository, property: KProperty<*>): Boolean {
                return thisRef.sharedPreferences.getBoolean(key, default)
            }

            override fun setValue(
                thisRef: PreferencesRepository,
                property: KProperty<*>,
                value: Boolean
            ) {
                thisRef.sharedPreferences.edit().putBoolean(key, value).apply()
            }

        }
    }

    companion object {
        private const val IS_DATA_LOADED = "is_data_loaded"
    }
}