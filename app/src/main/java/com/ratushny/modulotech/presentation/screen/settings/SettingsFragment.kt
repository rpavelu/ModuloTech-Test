package com.ratushny.modulotech.presentation.screen.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.ratushny.modulotech.R
import timber.log.Timber

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var prefs: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        prefs = context?.getSharedPreferences(
            getString(R.string.settings_preference),
            Context.MODE_PRIVATE
        ) ?: return

        // Dark theme
        val themeSwitch = findPreference<SwitchPreference>(THEME_PREFERENCE_KEY)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themeSwitch?.isChecked = true
        }

        themeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue.toString().toBoolean()) {
                prefs.edit().putBoolean(DARK_THEME_PREF_KEY, true).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES).also {
                    Timber.i("Dark theme enabled in settings")
                }
            } else {
                prefs.edit().putBoolean(DARK_THEME_PREF_KEY, false).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO).also {
                    Timber.i("Dark theme disabled in settings")
                }
            }
            true
        }
    }

    companion object {
        const val DARK_THEME_PREF_KEY = "dark_theme"
        const val THEME_PREFERENCE_KEY = "theme_preference"
    }
}