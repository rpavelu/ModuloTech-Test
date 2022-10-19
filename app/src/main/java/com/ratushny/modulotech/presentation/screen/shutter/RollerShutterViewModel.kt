package com.ratushny.modulotech.presentation.screen.shutter

import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.model.device.RollerShutter
import com.ratushny.modulotech.presentation.extensions.update
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch

class RollerShutterViewModel(
    private val deviceInteractor: DeviceInteractor
) : BaseViewModel<RollerShutterScreenState>() {

    fun setDevice(device: RollerShutter) {
        screenStateMutable.update {
            it.copy(
                rollerShutter = device.copy()
            )
        }
    }

    fun setPosition(position: Int) {
        screenStateMutable.update {
            it.copy(
                rollerShutter = it.rollerShutter.copy(
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

    override fun createInitialState(): RollerShutterScreenState {
        return RollerShutterScreenState(
            RollerShutter(
                id = 0,
                deviceName = "",
                position = 0,
            )
        )
    }

    override fun onAttached() {
        // Not needed
    }
}