package com.ratushny.modulotech.data.repository

import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import com.ratushny.modulotech.data.network.ModuloService
import com.ratushny.modulotech.data.network.model.DeviceResponse
import com.ratushny.modulotech.data.network.model.ProductTypeResponse
import com.ratushny.modulotech.data.network.model.UserResponse
import com.ratushny.modulotech.data.preferences.PreferencesEntry
import com.ratushny.modulotech.domain.api.ModuloRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModuloRepository(
    private val service: ModuloService,
    private val preferencesEntry: PreferencesEntry,
) : ModuloRepositoryApi {

    override suspend fun loadDevices(): List<DeviceResponse> =
        withContext(Dispatchers.IO) {
            val filteredProductTypes = createFilteredProductTypesSet()

            service.loadData().devices.filter { filteredProductTypes.contains(it.productType) }
        }

    override suspend fun loadUser(): UserResponse =
        withContext(Dispatchers.IO) {
            service.loadData().user
        }

    private fun createFilteredProductTypesSet(): Set<ProductTypeResponse> {
        val filterSet: ArraySet<ProductTypeResponse> = arraySetOf()

        if (isLightFilterEnabled())
            filterSet.add(ProductTypeResponse.Light)

        if (isHeaterFilterEnabled())
            filterSet.add(ProductTypeResponse.Heater)

        if (isRollerShutterFilterEnabled())
            filterSet.add(ProductTypeResponse.RollerShutter)

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