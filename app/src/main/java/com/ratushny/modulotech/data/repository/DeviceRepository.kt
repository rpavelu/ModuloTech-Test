package com.ratushny.modulotech.data.repository

import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import com.ratushny.modulotech.data.database.dao.DeviceDao
import com.ratushny.modulotech.data.database.mapper.convertToAppEntity
import com.ratushny.modulotech.data.database.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.data.preferences.PreferencesEntry
import com.ratushny.modulotech.domain.api.DeviceRepositoryApi
import com.ratushny.modulotech.domain.entity.device.Device
import com.ratushny.modulotech.domain.entity.device.ProductType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceRepository(
    private val deviceDao: DeviceDao,
    private val preferencesEntry: PreferencesEntry,
) : DeviceRepositoryApi {

    override suspend fun loadDevices(): List<Device> =
        withContext(Dispatchers.IO) {
            val filteredProductTypes = createFilteredProductTypesSet()

            deviceDao.selectAll()
                .filter { filteredProductTypes.contains(it.productType) }
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

    private fun createFilteredProductTypesSet(): Set<ProductType> {
        val filterSet: ArraySet<ProductType> = arraySetOf()

        if (isLightFilterEnabled())
            filterSet.add(ProductType.LIGHT)

        if (isHeaterFilterEnabled())
            filterSet.add(ProductType.HEATER)

        if (isRollerShutterFilterEnabled())
            filterSet.add(ProductType.ROLLERSHUTTER)

        return filterSet
    }

    override fun isLightFilterEnabled(): Boolean =
        preferencesEntry.readBoolean(PreferencesEntry.IS_LIGHT_FILTER_ENABLED)

    override fun isHeaterFilterEnabled(): Boolean =
        preferencesEntry.readBoolean(PreferencesEntry.IS_HEATER_FILTER_ENABLED)

    override fun isRollerShutterFilterEnabled(): Boolean =
        preferencesEntry.readBoolean(PreferencesEntry.IS_ROLLER_SHUTTER_FILTER_ENABLED)

    override fun setLightFilterEnabled(enabled: Boolean) =
        preferencesEntry.saveBoolean(PreferencesEntry.IS_LIGHT_FILTER_ENABLED, enabled)

    override fun setHeaterFilterEnabled(enabled: Boolean) =
        preferencesEntry.saveBoolean(PreferencesEntry.IS_HEATER_FILTER_ENABLED, enabled)

    override fun setRollerShutterFilterEnabled(enabled: Boolean) =
        preferencesEntry.saveBoolean(PreferencesEntry.IS_ROLLER_SHUTTER_FILTER_ENABLED, enabled)
}