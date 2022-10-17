package com.ratushny.modulotech.presentation.di

import com.ratushny.modulotech.presentation.screen.deviceslist.DevicesListFragment
import com.ratushny.modulotech.presentation.screen.deviceslist.DevicesListViewModel
import com.ratushny.modulotech.presentation.screen.heater.HeaterFragment
import com.ratushny.modulotech.presentation.screen.heater.HeaterViewModel
import com.ratushny.modulotech.presentation.screen.light.LightFragment
import com.ratushny.modulotech.presentation.screen.light.LightViewModel
import com.ratushny.modulotech.presentation.screen.settings.SettingsFragment
import com.ratushny.modulotech.presentation.screen.shutter.RollerShutterFragment
import com.ratushny.modulotech.presentation.screen.shutter.RollerShutterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val deviceListFragmentModule = module {
    scope<DevicesListFragment> {
        viewModel {
            DevicesListViewModel(moduloInteractor = get())
        }
    }
}

val settingsFragmentModule = module {
    single { SettingsFragment() }
}

val lightFragmentModule = module {
    scope<LightFragment> {
        viewModel {
            LightViewModel()
        }
    }
}

val heaterFragmentModule = module {
    scope<HeaterFragment> {
        viewModel {
            HeaterViewModel()
        }
    }
}

val rollerShutterFragmentModule = module {
    scope<RollerShutterFragment> {
        viewModel {
            RollerShutterViewModel()
        }
    }
}