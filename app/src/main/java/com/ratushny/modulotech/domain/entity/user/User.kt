package com.ratushny.modulotech.domain.entity.user

import java.util.Date

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address?,
    val birthDate: Date,
)
