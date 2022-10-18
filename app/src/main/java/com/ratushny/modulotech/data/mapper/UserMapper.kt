package com.ratushny.modulotech.data.mapper

import com.ratushny.modulotech.data.network.model.UserResponse
import com.ratushny.modulotech.domain.entity.user.Address
import com.ratushny.modulotech.domain.entity.user.User
import java.util.*

fun UserResponse.convertToAppEntity(): User =
    User(
        firstName = firstName,
        lastName = lastName,
        birthDate = Date(birthDate),
        address = Address(
            address.city,
            address.postalCode,
            address.street,
            address.streetCode,
            address.country,
        ),
    )