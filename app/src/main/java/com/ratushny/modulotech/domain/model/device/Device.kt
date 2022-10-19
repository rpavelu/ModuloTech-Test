package com.ratushny.modulotech.domain.model.device

sealed class Device(
    val id: Long,
    val deviceName: String,
) : java.io.Serializable

class Light(
    id: Long,
    deviceName: String,
    val mode: DeviceMode,
    val intensity: Int,
) : Device(id, deviceName)

class Heater(
    id: Long,
    deviceName: String,
    val mode: DeviceMode,
    val temperature: Float,
) : Device(id, deviceName)

class RollerShutter(
    id: Long,
    deviceName: String,
    val position: Int,
) : Device(id, deviceName)