package com.ratushny.modulotech.domain.model.user

data class Address(
    val city: String,
    val postalCode: Int,
    val street: String,
    val streetCode: String,
    val country: String,
)