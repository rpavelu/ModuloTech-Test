package com.ratushny.modulotech.data.database.mapper

import com.ratushny.modulotech.data.database.entity.AddressEntity
import com.ratushny.modulotech.domain.model.user.Address

fun AddressEntity.convertToAppEntity(): Address = Address(
    city = city,
    postalCode = postalCode,
    street = street,
    streetCode = streetCode,
    country = country,
)

fun Address.convertToDatabaseEntity(): AddressEntity = AddressEntity(
    city = city,
    postalCode = postalCode,
    street = street,
    streetCode = streetCode,
    country = country,
)