package com.ratushny.modulotech.domain.api

import com.ratushny.modulotech.domain.model.device.Device

interface DeviceRepositoryApi {

    suspend fun loadDevices(): List<Device>

    suspend fun updateDevice(device: Device)

    suspend fun deleteDevice(device: Device)

}