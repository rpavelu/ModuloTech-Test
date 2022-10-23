package com.ratushny.modulotech.data.network.mapper

import com.ratushny.modulotech.data.database.entity.AddressEntity
import com.ratushny.modulotech.data.database.entity.UserEntity
import com.ratushny.modulotech.data.network.model.UserResponse
import java.util.Date

fun UserResponse.convertToDatabaseEntity(): UserEntity =
    UserEntity(
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        birthdate = Date(birthDate ?: 0),
        address = AddressEntity(
            address?.city ?: "",
            address?.postalCode ?: "",
            address?.street ?: "",
            address?.streetCode ?: "",
            address?.country ?: "",
        ),
    )