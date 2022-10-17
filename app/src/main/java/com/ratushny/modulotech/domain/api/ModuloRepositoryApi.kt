package com.ratushny.modulotech.domain.api

import com.ratushny.modulotech.data.network.model.DeviceResponse
import com.ratushny.modulotech.data.network.model.UserResponse

interface ModuloRepositoryApi {

    suspend fun loadDevices(): List<DeviceResponse>

    suspend fun loadUser(): UserResponse

    fun isLightFilterEnabled(): Boolean

    fun isHeaterFilterEnabled(): Boolean

    fun isRollerShutterFilterEnabled(): Boolean

    fun setLightFilterEnabled(enabled: Boolean)

    fun setHeaterFilterEnabled(enabled: Boolean)

    fun setRollerShutterFilterEnabled(enabled: Boolean)
}