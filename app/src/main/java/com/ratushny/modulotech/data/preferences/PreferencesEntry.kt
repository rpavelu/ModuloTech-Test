package com.ratushny.modulotech.data.preferences

import android.content.SharedPreferences

class PreferencesEntry(
    private val sharedPreferences: SharedPreferences
) {
    fun readBoolean(key: String, default: Boolean = true) =
        sharedPreferences.getBoolean(key, default)

    fun saveBoolean(key: String, value: Boolean) =
        save { it.putBoolean(key, value) }

    private fun save(action: (SharedPreferences.Editor) -> Unit) {
        sharedPreferences.edit().also {
            action.invoke(it)
            it.apply()
        }
    }

    companion object {
        const val IS_LIGHT_FILTER_ENABLED = "is_light_filter_enabled"
        const val IS_HEATER_FILTER_ENABLED = "is_heater_filter_enabled"
        const val IS_ROLLER_SHUTTER_FILTER_ENABLED = "is_roller_shutter_filter_enabled"

        const val IS_DATA_NOT_LOADED = "is_data_not_loaded"
    }
}