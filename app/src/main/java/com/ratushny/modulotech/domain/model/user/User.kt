package com.ratushny.modulotech.domain.model.user

import java.util.Date

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthDate: Date,
)