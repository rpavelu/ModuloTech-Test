package com.ratushny.modulotech.presentation.screen.deviceslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.data.network.model.DeviceResponse
import com.ratushny.modulotech.data.network.model.ProductTypeResponse
import com.ratushny.modulotech.domain.interactor.ModuloInteractor
import kotlinx.coroutines.launch
import timber.log.Timber

class DevicesListViewModel(
    private val moduloInteractor: ModuloInteractor
) : ViewModel() {

    private lateinit var originalDeviceList: List<DeviceResponse>

    private val _moduloList = MutableLiveData<List<DeviceResponse>>()
    val moduloList: LiveData<List<DeviceResponse>>
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

    fun updateFilterByProductType(productType: ProductTypeResponse, enabled: Boolean) =
        when (productType) {
            ProductTypeResponse.Light -> _isLightFilterEnabled.value =
                enabled.also { moduloInteractor.setLightFilterEnabled(enabled) }
            ProductTypeResponse.Heater -> _isHeaterFilterEnabled.value =
                enabled.also { moduloInteractor.setHeaterFilterEnabled(enabled) }
            ProductTypeResponse.RollerShutter -> _isRollerShutterFilterEnabled.value =
                enabled.also { moduloInteractor.setRollerShutterFilterEnabled(enabled) }
        }

    fun refreshDeviceList() {
        viewModelScope.launch {
            try {
                _isLightFilterEnabled.value =
                    moduloInteractor.isLightFilterEnabled()
                _isHeaterFilterEnabled.value =
                    moduloInteractor.isHeaterFilterEnabled()
                _isRollerShutterFilterEnabled.value =
                    moduloInteractor.isRollerShutterFilterEnabled()

                _moduloList.value = moduloInteractor.loadDevices().also {
                    originalDeviceList = it
                }
            } catch (error: Throwable) {
                Timber.e("Error loading device list: $error")
            }
        }
    }
}