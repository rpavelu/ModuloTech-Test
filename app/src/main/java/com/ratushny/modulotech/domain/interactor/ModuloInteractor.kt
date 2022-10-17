package com.ratushny.modulotech.domain.interactor

import com.ratushny.modulotech.data.network.model.DeviceResponse
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi

class ModuloInteractor(
    private val repository: ModuloRepositoryApi
) {
    suspend fun loadDevices(): List<DeviceResponse> = repository.loadDevices()

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