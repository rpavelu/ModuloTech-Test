package com.ratushny.modulotech.data.repository

import com.ratushny.modulotech.data.database.dao.DeviceDao
import com.ratushny.modulotech.data.database.dao.UserDao
import com.ratushny.modulotech.data.database.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.data.network.ModuloService
import com.ratushny.modulotech.data.network.mapper.convertToAppEntity
import com.ratushny.modulotech.data.network.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi

class ModuloRepository(
    private val service: ModuloService,
    private val deviceDao: DeviceDao,
    private val userDao: UserDao,
) : ModuloRepositoryApi {

    override suspend fun refreshData(force: Boolean) {
        if (force || deviceDao.isEmpty()) {

            val response = service.loadData()

            val devices = response.devices
                .mapNotNull { it.convertToAppEntity() }
                .map { it.convertToDatabaseEntity() }
            deviceDao.deleteAll()
            deviceDao.insert(devices)

            val user = response.user.convertToDatabaseEntity()
            userDao.deleteAll()
            userDao.insert(user)
        }
    }
}