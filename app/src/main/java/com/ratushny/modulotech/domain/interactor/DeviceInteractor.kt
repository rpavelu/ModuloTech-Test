package com.ratushny.modulotech.domain.interactor

import com.ratushny.modulotech.domain.api.DeviceRepositoryApi
import com.ratushny.modulotech.domain.entity.device.Device

class DeviceInteractor(
    private val repository: DeviceRepositoryApi
) {

    suspend fun loadDevices(): List<Device> = repository.loadDevices()

    suspend fun updateDevice(device: Device) = repository.updateDevice(device)

    suspend fun deleteDevice(device: Device) = repository.deleteDevice(device)

    fun isLightFilterEnabled(): Boolean =
        repository.isLightFilterEnabled()

    fun isHeaterFilterEnabled(): Boolean =
        repository.isHeaterFilterEnabled()

    fun isRollerShutterFilterEnabled(): Boolean =
        repository.isRollerShutterFilterEnabled()

    fun setLightFilterEnabled(isEnabled: Boolean) =
        repository.setLightFilterEnabled(isEnabled)

    fun setHeaterFilterEnabled(isEnabled: Boolean) =
        repository.setHeaterFilterEnabled(isEnabled)

    fun setRollerShutterFilterEnabled(isEnabled: Boolean) =
        repository.setRollerShutterFilterEnabled(isEnabled)
}