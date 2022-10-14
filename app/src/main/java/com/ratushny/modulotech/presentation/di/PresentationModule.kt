package com.ratushny.modulotech.presentation.di

import com.ratushny.modulotech.presentation.screen.deviceslist.DevicesListFragment
import com.ratushny.modulotech.presentation.screen.deviceslist.DevicesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val deviceListFragmentModule = module {
    scope<DevicesListFragment> {
        viewModel {
            DevicesListViewModel(moduloInteractor = get())
        }
    }
}