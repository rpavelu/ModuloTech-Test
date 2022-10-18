package com.ratushny.modulotech.data.repository

import com.ratushny.modulotech.data.database.dao.DeviceDao
import com.ratushny.modulotech.data.database.dao.UserDao
import com.ratushny.modulotech.data.database.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.data.mapper.convertToAppEntity
import com.ratushny.modulotech.data.network.ModuloService
import com.ratushny.modulotech.data.preferences.PreferencesEntry
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModuloRepository(
    private val service: ModuloService,
    private val deviceDao: DeviceDao,
    private val userDao: UserDao,
    private val preferencesEntry: PreferencesEntry,
) : ModuloRepositoryApi {

    override suspend fun loadData() =
        withContext(Dispatchers.IO) {
            if (preferencesEntry.readBoolean(PreferencesEntry.IS_DATA_NOT_LOADED)) {
                forceRefreshData()

                preferencesEntry.saveBoolean(PreferencesEntry.IS_DATA_NOT_LOADED, false)
            }
        }

    override suspend fun forceRefreshData() =
        withContext(Dispatchers.IO) {
            val response = service.loadData()

            // Load devices
            val devices = response.devices.map { it.convertToAppEntity() }
                .map { it.convertToDatabaseEntity() }
            deviceDao.deleteAll()
            deviceDao.insert(devices)

            // Load user
            val user = response.user.convertToAppEntity().convertToDatabaseEntity()
            userDao.deleteAll()
            userDao.insert(user)
        }
}