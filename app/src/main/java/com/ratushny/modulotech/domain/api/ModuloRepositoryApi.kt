package com.ratushny.modulotech.domain.api

import com.ratushny.modulotech.data.model.Device

interface ModuloRepositoryApi {

    suspend fun loadDevices(): List<Device>
}