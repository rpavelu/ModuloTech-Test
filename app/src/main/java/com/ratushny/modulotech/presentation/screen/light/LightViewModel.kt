package com.ratushny.modulotech.presentation.screen.light

import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.model.device.Device.Light
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.presentation.extensions.update
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch

class LightViewModel(
    private val deviceInteractor: DeviceInteractor
) : BaseViewModel<LightScreenState>() {

    override val initialState: LightScreenState
        get() = LightScreenState(
            Light(
                id = 0,
                deviceName = "",
                mode = DeviceMode.OFF,
                intensity = 0,
            )
        )

    fun setDevice(device: Light) {
        _screenState.update {
            copy(
                light = device.copy()
            )
        }
    }

    fun setMode(isEnabled: Boolean) {
        _screenState.update {
            copy(
                light = light.copy(
                    mode = if (isEnabled) DeviceMode.ON else DeviceMode.OFF
                )
            )
        }
        updateDeviceValues()
    }

    fun setIntensity(intensity: Int) {
        _screenState.update {
            copy(
                light = light.copy(
                    intensity = intensity
                )
            )
        }
        updateDeviceValues()
    }

    private fun updateDeviceValues() {
        viewModelScope.launch {
            screenState.value?.let {
                deviceInteractor.updateDevice(it.light.copy())
            }
        }
    }

    override fun onAttached() {
        // Not needed
    }
}