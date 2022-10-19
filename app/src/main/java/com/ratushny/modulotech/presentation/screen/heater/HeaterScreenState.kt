package com.ratushny.modulotech.presentation.screen.heater

import com.ratushny.modulotech.domain.model.device.Heater

data class HeaterScreenState(
    val heater: Heater,
    val minTemp: Int,
    val maxTemp: Int,
    val temperatureStep: Float,
    val currentRawTemp: Int
)
