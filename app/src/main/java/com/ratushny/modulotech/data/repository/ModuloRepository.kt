package com.ratushny.modulotech.data.repository

import com.ratushny.modulotech.data.database.dao.DeviceDao
import com.ratushny.modulotech.data.database.dao.UserDao
import com.ratushny.modulotech.data.database.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.data.network.ModuloService
import com.ratushny.modulotech.data.network.mapper.convertToAppEntity
import com.ratushny.modulotech.data.repository.preferences.PreferencesRepository
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi

class ModuloRepository(
    private val service: ModuloService,
    private val deviceDao: DeviceDao,
    private val userDao: UserDao,
    private val preferencesRepository: PreferencesRepository,
) : ModuloRepositoryApi {

    override suspend fun refreshData(force: Boolean) {
        if (force || !preferencesRepository.readBoolean(PreferencesRepository.IS_DATA_LOADED)) {

            val response = service.loadData()

            val devices = response.devices.map { it.convertToAppEntity() }
                .map { it.convertToDatabaseEntity() }
            deviceDao.deleteAll()
            deviceDao.insert(devices)

            val user = response.user.convertToAppEntity().convertToDatabaseEntity()
            userDao.deleteAll()
            userDao.insert(user)

            preferencesRepository.saveBoolean(PreferencesRepository.IS_DATA_LOADED, true)
        }
    }
}