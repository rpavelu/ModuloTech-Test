package com.ratushny.modulotech.data.database.mapper

import com.ratushny.modulotech.data.database.entity.UserEntity
import com.ratushny.modulotech.domain.model.user.User

fun UserEntity.convertToAppEntity(): User = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    address = address.convertToAppEntity(),
    birthDate = birthdate,
)

fun User.convertToDatabaseEntity(): UserEntity = UserEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    address = address.convertToDatabaseEntity(),
    birthdate = birthDate,
)