package com.ratushny.modulotech.presentation.screen.deviceslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.data.model.Device
import com.ratushny.modulotech.domain.interactor.ModuloInteractor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import timber.log.Timber

class DevicesListViewModel(
    private val moduloInteractor: ModuloInteractor
) : ViewModel(), KoinComponent {

    private val _moduloList = MutableLiveData<List<Device>>()
    val moduloList: LiveData<List<Device>>
        get() = _moduloList

    fun getDeviceList() {
        viewModelScope.launch {
            try {
                _moduloList.value = moduloInteractor.loadDevices()
            } catch (error: Throwable) {
                Timber.e("Error loading device list: $error")
            }
        }
    }
}