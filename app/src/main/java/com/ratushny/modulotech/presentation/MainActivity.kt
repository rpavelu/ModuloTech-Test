package com.ratushny.modulotech.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.ActivityMainBinding
import org.koin.androidx.scope.ScopeActivity

class MainActivity : ScopeActivity() {

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.DevicesListFragment,
                R.id.ProfileFragment,
            )
        )
    }
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            with(binding) {
                navHostFragmentContentMain.apply {
                    setPadding(
                        paddingLeft,
                        paddingTop,
                        paddingRight,
                        bottomNavigation.measuredHeight
                    )
                }

                toolbar.apply {
                    setPadding(paddingLeft, insets.top, paddingRight, paddingBottom)
                }

                bottomNavigation.apply {
                    setPadding(paddingLeft, paddingTop, paddingRight, insets.bottom)
                }

                bottomNavigation.setOnItemSelectedListener {
                    when (val id = it.itemId) {
                        R.id.devices_page -> {
                            if (id != bottomNavigation.selectedItemId) {
                                navController.navigate(R.id.open_devices_fragment)
                            }
                            true
                        }
                        R.id.profile_page -> {
                            if (id != bottomNavigation.selectedItemId) {
                                navController.navigate(R.id.open_profile_fragment)
                            }
                            true
                        }
                        else -> false
                    }
                }
            }

            WindowInsetsCompat.CONSUMED
        }
    }


    override fun onSupportNavigateUp(): Boolean = navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
}