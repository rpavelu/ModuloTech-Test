package com.ratushny.modulotech.data.repository.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class PreferencesRepository(
    private val context: Context
) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            context.packageName,
            Application.MODE_PRIVATE
        )
    }

    // TODO: Change default value to false
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
        const val IS_DATA_NOT_LOADED = "is_data_not_loaded"
    }
}