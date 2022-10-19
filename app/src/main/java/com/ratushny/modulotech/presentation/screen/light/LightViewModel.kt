package com.ratushny.modulotech.presentation.screen.light

import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.domain.model.device.Light
import com.ratushny.modulotech.presentation.extensions.update
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch

class LightViewModel(
    private val deviceInteractor: DeviceInteractor
) : BaseViewModel<LightScreenState>() {

    fun setDevice(device: Light) {
        screenStateMutable.update {
            it.copy(
                light = device.copy()
            )
        }
    }

    fun setMode(isEnabled: Boolean) {
        screenStateMutable.update {
            it.copy(
                light = it.light.copy(
                    mode = if (isEnabled) DeviceMode.ON else DeviceMode.OFF
                )
            )
        }
        updateDeviceValues()
    }

    fun setIntensity(intensity: Int) {
        screenStateMutable.update {
            it.copy(
                light = it.light.copy(
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

    override fun createInitialState(): LightScreenState {
        return LightScreenState(
            Light(
                id = 0,
                deviceName = "",
                mode = DeviceMode.OFF,
                intensity = 0,
            )
        )
    }

    override fun onAttached() {
        // Not needed
    }
}