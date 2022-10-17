package com.ratushny.modulotech.data.network.model

data class UserResponse(
    val firstName: String,
    val lastName: String,
    val address: AddressResponse,
    val birthDate: Long,
)