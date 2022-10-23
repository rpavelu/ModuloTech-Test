package com.ratushny.modulotech.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerResponse(
    @Json(name = "devices") val devices: List<DeviceResponse>,
    @Json(name = "user") val user: UserResponse,
)