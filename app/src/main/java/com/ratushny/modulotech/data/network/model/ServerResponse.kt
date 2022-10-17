package com.ratushny.modulotech.data.network.model

data class ServerResponse(
    val devices: List<DeviceResponse>,
    val user: UserResponse,
)