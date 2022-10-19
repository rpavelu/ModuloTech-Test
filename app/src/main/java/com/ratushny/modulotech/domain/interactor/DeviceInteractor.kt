package com.ratushny.modulotech.domain.interactor

import com.ratushny.modulotech.domain.api.DeviceRepositoryApi
import com.ratushny.modulotech.domain.model.device.Device

class DeviceInteractor(
    private val repository: DeviceRepositoryApi
) {

    suspend fun loadDevices(): List<Device> = repository.loadDevices()

    suspend fun updateDevice(device: Device) = repository.updateDevice(device)

    suspend fun deleteDevice(device: Device) = repository.deleteDevice(device)

}