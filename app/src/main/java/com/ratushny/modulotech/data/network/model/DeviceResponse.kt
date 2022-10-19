package com.ratushny.modulotech.data.network.model

data class DeviceResponse(
    val id: Long?,
    val deviceName: String?,
    val intensity: Int?,
    val mode: DeviceModeResponse?,
    val temperature: Float?,
    val position: Int?,
    val productType: ProductTypeResponse?,
)