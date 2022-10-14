package com.ratushny.modulotech.data.repository

import com.ratushny.modulotech.data.model.Device
import com.ratushny.modulotech.data.network.ModuloService
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModuloRepository(
    private val service: ModuloService
) :
    ModuloRepositoryApi {
    override suspend fun loadDevices(): List<Device> =
        withContext(Dispatchers.IO) {
            service.loadData().devices
        }
}