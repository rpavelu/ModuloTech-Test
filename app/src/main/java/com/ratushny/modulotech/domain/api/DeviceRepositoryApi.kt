package com.ratushny.modulotech.domain.api

import com.ratushny.modulotech.domain.entity.device.Device

interface DeviceRepositoryApi {

    suspend fun loadDevices(): List<Device>

    suspend fun updateDevice(device: Device)

    suspend fun deleteDevice(device: Device)

    fun isLightFilterEnabled(): Boolean

    fun isHeaterFilterEnabled(): Boolean

    fun isRollerShutterFilterEnabled(): Boolean

    fun setLightFilterEnabled(enabled: Boolean)

    fun setHeaterFilterEnabled(enabled: Boolean)

    fun setRollerShutterFilterEnabled(enabled: Boolean)
}