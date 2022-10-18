package com.ratushny.modulotech.presentation

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.ActivityMainBinding
import org.koin.androidx.scope.ScopeActivity

class MainActivity : ScopeActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment_content_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.DevicesListFragment,
                    R.id.ProfileFragment,
                    R.id.SettingsFragment,
                )
            )

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.devices_page -> {
                    if (it.itemId != binding.bottomNavigation.selectedItemId)
                        navController.navigate(R.id.open_devices_fragment)
                    true
                }
                R.id.profile_page -> {
                    if (it.itemId != binding.bottomNavigation.selectedItemId)
                        navController.navigate(R.id.open_profile_fragment)
                    true
                }
                R.id.settings_page -> {
                    navController.navigate(R.id.open_setting_fragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
}