package com.ratushny.modulotech.data.model

data class ServerResponse(
    val devices: List<Device>,
    val user: User
)