package com.ratushny.modulotech.presentation.screen.shutter

import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.model.device.Device.RollerShutter
import com.ratushny.modulotech.presentation.extensions.update
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch

class RollerShutterViewModel(
    private val deviceInteractor: DeviceInteractor
) : BaseViewModel<RollerShutterScreenState>() {

    init {
        _screenState.value = RollerShutterScreenState(
            RollerShutter(
                id = 0,
                deviceName = "",
                position = 0,
            )
        )
    }

    fun setDevice(device: RollerShutter) {
        _screenState.update {
            copy(
                rollerShutter = device.copy()
            )
        }
    }

    fun setPosition(position: Int) {
        _screenState.update {
            copy(
                rollerShutter = rollerShutter.copy(
                    position = position
                )
            )
        }
        updateDeviceValues()
    }

    private fun updateDeviceValues() {
        viewModelScope.launch {
            screenState.value?.let {
                deviceInteractor.updateDevice(it.rollerShutter.copy())
            }
        }
    }
}