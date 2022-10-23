package com.ratushny.modulotech.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceResponse(
    @Json(name = "id") val id: Long?,
    @Json(name = "deviceName") val deviceName: String?,
    @Json(name = "intensity") val intensity: Int?,
    @Json(name = "mode") val mode: DeviceModeResponse?,
    @Json(name = "temperature") val temperature: Float?,
    @Json(name = "position") val position: Int?,
    @Json(name = "productType") val productType: ProductTypeResponse?,
)