package com.ratushny.modulotech.presentation.screen.shutter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.entity.device.RollerShutter
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import kotlinx.coroutines.launch

class RollerShutterViewModel(
    private val deviceInteractor: DeviceInteractor
) : ViewModel() {

    private val _position = MutableLiveData<Int>()
    val position: LiveData<Int>
        get() = _position

    fun setPosition(position: Int, device: RollerShutter) {
        _position.value = position

        updateDeviceValues(device)
    }

    fun setDeviceValues(device: RollerShutter) {
        _position.value = device.position
    }

    private fun updateDeviceValues(device: RollerShutter) {
        viewModelScope.launch {
            deviceInteractor.updateDevice(
                RollerShutter(
                    id = device.id,
                    deviceName = device.deviceName,
                    position = _position.value ?: 0
                )
            )
        }
    }
}