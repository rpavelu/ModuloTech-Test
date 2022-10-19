package com.ratushny.modulotech.presentation.screen.shutter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.model.device.RollerShutter
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch

class RollerShutterViewModel(
    private val deviceInteractor: DeviceInteractor
) : BaseViewModel<RollerShutterScreenState>() {

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

    override fun createInitialState(): RollerShutterScreenState {
        return RollerShutterScreenState()
    }

    override fun onAttached() {
        //TODO
    }
}