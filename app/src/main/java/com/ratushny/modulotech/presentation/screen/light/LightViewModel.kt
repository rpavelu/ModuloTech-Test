package com.ratushny.modulotech.presentation.screen.light

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.domain.model.device.Light
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch

class LightViewModel(
    private val deviceInteractor: DeviceInteractor
) : BaseViewModel<LightScreenState>() {
    private val _mode = MutableLiveData<Boolean>()
    val mode: LiveData<Boolean>
        get() = _mode

    private val _intensity = MutableLiveData<Int>()
    val intensity: LiveData<Int>
        get() = _intensity

    fun setMode(isEnabled: Boolean, device: Light) {
        _mode.value = isEnabled

        updateDeviceValues(device)
    }

    fun setIntensity(progress: Int, device: Light) {
        _intensity.value = progress

        updateDeviceValues(device)
    }

    fun setDeviceValues(device: Light) {
        _mode.value = setModeBasedOnValue(device.mode)
        _intensity.value = device.intensity
    }

    private fun setModeBasedOnValue(mode: DeviceMode): Boolean = mode == DeviceMode.ON

    private fun updateDeviceValues(device: Light) {
        viewModelScope.launch {
            deviceInteractor.updateDevice(
                Light(
                    id = device.id,
                    deviceName = device.deviceName,
                    mode = if (_mode.value == true) DeviceMode.ON else DeviceMode.OFF,
                    intensity = _intensity.value ?: 0
                )
            )
        }
    }

    override fun createInitialState(): LightScreenState {
        return LightScreenState()
    }

    override fun onAttached() {
        //TODO
    }
}