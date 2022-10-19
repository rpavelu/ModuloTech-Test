package com.ratushny.modulotech.domain.model.user

data class Address(
    val city: String,
    val postalCode: String,
    val street: String,
    val streetCode: String,
    val country: String,
)