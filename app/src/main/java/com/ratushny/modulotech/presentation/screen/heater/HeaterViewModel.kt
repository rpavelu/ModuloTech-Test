package com.ratushny.modulotech.presentation.screen.heater

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.domain.model.device.Heater
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch

class HeaterViewModel(
    private val deviceInteractor: DeviceInteractor
) : BaseViewModel<HeaterScreenState>() {

    private val _mode = MutableLiveData<Boolean>()
    val mode: LiveData<Boolean>
        get() = _mode

    private val _temperature = MutableLiveData<Float>()
    val temperature: LiveData<Float>
        get() = _temperature

    fun setMode(isEnabled: Boolean, device: Heater) {
        _mode.value = isEnabled

        updateDeviceValues(device)
    }

    fun setTemperature(temperature: Float, device: Heater) {
        _temperature.value = temperature

        updateDeviceValues(device)
    }

    fun setDeviceValues(device: Heater) {
        _mode.value = setModeBasedOnValue(device.mode)
        _temperature.value = device.temperature
    }

    private fun setModeBasedOnValue(mode: DeviceMode): Boolean = mode == DeviceMode.ON

    private fun updateDeviceValues(device: Heater) {
        viewModelScope.launch {
            deviceInteractor.updateDevice(
                Heater(
                    id = device.id,
                    deviceName = device.deviceName,
                    mode = if (_mode.value == true) DeviceMode.ON else DeviceMode.OFF,
                    temperature = _temperature.value ?: 7.0f
                )
            )
        }
    }

    override fun createInitialState(): HeaterScreenState {
        return HeaterScreenState()
    }

    override fun onAttached() {
        //TODO
    }
}