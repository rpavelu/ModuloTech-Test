package com.ratushny.modulotech.presentation.screen.settings

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.ratushny.modulotech.R
import com.ratushny.modulotech.data.preferences.PreferencesEntry
import org.koin.android.ext.android.inject
import timber.log.Timber

class SettingsFragment : PreferenceFragmentCompat() {

    private val prefs: PreferencesEntry by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // Dark theme
        val themeSwitch = findPreference<SwitchPreference>(THEME_PREFERENCE_KEY)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            themeSwitch?.isChecked = true
        }

        themeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue.toString().toBoolean()) {
                prefs.saveBoolean(DARK_THEME_PREF_KEY, true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES).also {
                    Timber.i("Dark theme enabled in settings")
                }
            } else {
                prefs.saveBoolean(DARK_THEME_PREF_KEY, false)
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