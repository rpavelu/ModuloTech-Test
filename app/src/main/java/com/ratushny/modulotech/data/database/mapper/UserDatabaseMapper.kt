package com.ratushny.modulotech.data.database.mapper

import com.ratushny.modulotech.data.database.entity.UserEntity
import com.ratushny.modulotech.domain.entity.user.User
import java.util.*

fun UserEntity.convertToAppEntity(): User = User(
    firstName = firstName ?: "",
    lastName = lastName ?: "",
    address = address?.convertToAppEntity(),
    birthDate = birthdate ?: Date(),
)

fun User.convertToDatabaseEntity(): UserEntity = UserEntity(
    firstName = firstName,
    lastName = lastName,
    address = address?.convertToDatabaseEntity(),
    birthdate = birthDate,
)