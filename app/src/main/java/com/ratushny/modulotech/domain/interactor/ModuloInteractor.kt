package com.ratushny.modulotech.domain.interactor

import com.ratushny.modulotech.data.model.Device
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi

class ModuloInteractor(
    private val repository: ModuloRepositoryApi
) {
    suspend fun loadDevices(): List<Device> = repository.loadDevices()
}