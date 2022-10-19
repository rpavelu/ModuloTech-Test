package com.ratushny.modulotech.data.network.mapper

import com.ratushny.modulotech.data.network.model.UserResponse
import com.ratushny.modulotech.domain.model.user.Address
import com.ratushny.modulotech.domain.model.user.User
import java.util.*

fun UserResponse.convertToAppEntity(): User =
    User(
        id = -1,
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        birthDate = Date(birthDate ?: 0),
        address = Address(
            address?.city ?: "",
            address?.postalCode ?: 0,
            address?.street ?: "",
            address?.streetCode ?: "",
            address?.country ?: "",
        ),
    )