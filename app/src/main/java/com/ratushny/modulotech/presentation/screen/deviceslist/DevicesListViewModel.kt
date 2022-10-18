package com.ratushny.modulotech.presentation.screen.deviceslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.entity.device.Device
import com.ratushny.modulotech.domain.entity.device.ProductType
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.interactor.ModuloInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class DevicesListViewModel(
    private val moduloInteractor: ModuloInteractor,
    private val deviceInteractor: DeviceInteractor,
) : ViewModel() {

    private val _moduloList = MutableLiveData<List<Device>>()
    val moduloList: LiveData<List<Device>>
        get() = _moduloList

    private val _isLightFilterEnabled = MutableLiveData<Boolean>()
    val isLightFilterEnabled: LiveData<Boolean>
        get() = _isLightFilterEnabled

    private val _isHeaterFilterEnabled = MutableLiveData<Boolean>()
    val isHeaterFilterEnabled: LiveData<Boolean>
        get() = _isHeaterFilterEnabled

    private val _isRollerShutterFilterEnabled = MutableLiveData<Boolean>()
    val isRollerShutterFilterEnabled: LiveData<Boolean>
        get() = _isRollerShutterFilterEnabled

    fun updateFilterByProductType(productType: ProductType, enabled: Boolean) =
        when (productType) {
            ProductType.LIGHT -> _isLightFilterEnabled.value =
                enabled.also { deviceInteractor.setLightFilterEnabled(enabled) }
            ProductType.HEATER -> _isHeaterFilterEnabled.value =
                enabled.also { deviceInteractor.setHeaterFilterEnabled(enabled) }
            ProductType.ROLLERSHUTTER -> _isRollerShutterFilterEnabled.value =
                enabled.also { deviceInteractor.setRollerShutterFilterEnabled(enabled) }
        }

    fun loadData() {
        viewModelScope.launch {
            moduloInteractor.loadData()
            refreshDeviceList()
        }
    }

    fun removeDevice(device: Device) {
        viewModelScope.launch(Dispatchers.Main) {
            deviceInteractor.deleteDevice(device)
            _moduloList.value = _moduloList.value?.filter { it != device }
        }
    }

    fun forceRefreshData() {
        viewModelScope.launch {
            moduloInteractor.forceRefreshData()
            refreshDeviceList()
        }
    }

    fun refreshDeviceList() {
        viewModelScope.launch {
            try {
                _isLightFilterEnabled.value =
                    deviceInteractor.isLightFilterEnabled()
                _isHeaterFilterEnabled.value =
                    deviceInteractor.isHeaterFilterEnabled()
                _isRollerShutterFilterEnabled.value =
                    deviceInteractor.isRollerShutterFilterEnabled()

                _moduloList.value = deviceInteractor.loadDevices()
            } catch (error: Throwable) {
                Timber.e("Error loading device list: $error")
            }
        }
    }
}