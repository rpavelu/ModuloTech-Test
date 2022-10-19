package com.ratushny.modulotech.presentation.screen.heater

import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.domain.model.device.Heater
import com.ratushny.modulotech.presentation.extensions.update
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch

class HeaterViewModel(
    private val deviceInteractor: DeviceInteractor
) : BaseViewModel<HeaterScreenState>() {

    fun setDevice(device: Heater) {
        screenStateMutable.update {
            it.copy(
                heater = device.copy(),
                currentRawTemp = device.rawTemperature
            )
        }
    }

    fun setMode(isEnabled: Boolean) {
        screenStateMutable.update {
            it.copy(
                heater = it.heater.copy(
                    mode = if (isEnabled) DeviceMode.ON else DeviceMode.OFF
                )
            )
        }
        updateDeviceValues()
    }

    fun setRawTemperature(rawTemp: Int) {
        screenStateMutable.update {
            it.copy(
                heater = it.heater.copy(
                    temperature = (rawTemp.toFloat() * TEMPERATURE_STEP) + MIN_TEMPERATURE
                ),
                currentRawTemp = rawTemp
            )
        }
        updateDeviceValues()
    }

    private fun updateDeviceValues() {
        viewModelScope.launch {
            screenState.value?.let {
                deviceInteractor.updateDevice(it.heater.copy())
            }
        }
    }

    override fun createInitialState(): HeaterScreenState = HeaterScreenState(
        Heater(
            id = 0,
            deviceName = "",
            mode = DeviceMode.OFF,
            temperature = 7.0f,
        ),
        minTemp = MIN_TEMPERATURE,
        maxTemp = MAX_TEMPERATURE,
        temperatureStep = TEMPERATURE_STEP,
        currentRawTemp = 0
    )

    private val Heater.rawTemperature: Int
        get() = ((temperature - MIN_TEMPERATURE) / TEMPERATURE_STEP).toInt()

    override fun onAttached() {
        // Not needed
    }

    companion object {
        private const val MIN_TEMPERATURE = 7
        private const val MAX_TEMPERATURE = 28
        private const val TEMPERATURE_STEP = 0.5f
    }
}