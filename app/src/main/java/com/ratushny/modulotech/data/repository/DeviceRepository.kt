package com.ratushny.modulotech.data.repository

import com.ratushny.modulotech.data.database.dao.DeviceDao
import com.ratushny.modulotech.data.database.mapper.convertToAppEntity
import com.ratushny.modulotech.data.database.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.domain.api.DeviceRepositoryApi
import com.ratushny.modulotech.domain.model.device.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceRepository(
    private val deviceDao: DeviceDao
) : DeviceRepositoryApi {

    override suspend fun loadDevices(): List<Device> =
        withContext(Dispatchers.IO) {
            deviceDao.selectAll()
                .map { it.convertToAppEntity() }
        }

    override suspend fun updateDevice(device: Device) {
        withContext(Dispatchers.IO) {
            deviceDao.update(device.convertToDatabaseEntity())
        }
    }

    override suspend fun deleteDevice(device: Device) {
        withContext(Dispatchers.IO) {
            deviceDao.delete(device.convertToDatabaseEntity())
        }
    }

}