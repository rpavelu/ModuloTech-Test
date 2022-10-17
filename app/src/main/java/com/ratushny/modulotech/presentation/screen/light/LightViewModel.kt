package com.ratushny.modulotech.presentation.screen.light

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ratushny.modulotech.data.network.model.DeviceResponse

class LightViewModel : ViewModel() {
    private val _mode = MutableLiveData<Boolean>()
    val mode: LiveData<Boolean>
        get() = _mode

    private val _intensity = MutableLiveData<Int>()
    val intensity: LiveData<Int>
        get() = _intensity

    fun setDeviceValues(device: DeviceResponse) {
        _mode.value = setModeBasedOnValue(device.mode)
        _intensity.value = device.intensity
    }

    // TODO: Move to converter
    private fun setModeBasedOnValue(string: String): Boolean = string == "ON"

    fun setMode(isEnabled: Boolean) {
        _mode.value = isEnabled
    }

    fun setIntensity(progress: Int) {
        _intensity.value = progress
    }
}