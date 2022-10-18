package com.ratushny.modulotech.domain.entity.device

sealed class Device(
    val id: Long,
    val deviceName: String,
    val productType: ProductType,
) : java.io.Serializable

class Light(
    id: Long,
    deviceName: String,
    val mode: DeviceMode,
    val intensity: Int,
) : Device(id, deviceName, ProductType.LIGHT)

class Heater(
    id: Long,
    deviceName: String,
    val mode: DeviceMode,
    val temperature: Float,
) : Device(id, deviceName, ProductType.HEATER)

class RollerShutter(
    id: Long,
    deviceName: String,
    val position: Int,
) : Device(id, deviceName, ProductType.ROLLERSHUTTER)