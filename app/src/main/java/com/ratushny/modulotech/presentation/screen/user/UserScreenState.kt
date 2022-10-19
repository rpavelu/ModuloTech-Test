package com.ratushny.modulotech.presentation.screen.user

data class UserScreenState(
    val firstName: Field = Field(),
    val lastName: Field = Field(),
    val birthDate: Field = Field(),
    val city: Field = Field(),
    val postalCode: Field = Field(),
    val street: Field = Field(),
    val streetCode: Field = Field(),
    val country: Field = Field(),
) {

    val allFields: List<Field>
        get() = listOf(
            firstName,
            lastName,
            birthDate,
            city,
            postalCode,
            street,
            streetCode,
            country
        )

    data class Field(
        val value: String = "",
        var error: FieldError? = null
    )

    sealed class FieldError

    object EmptyFieldError : FieldError()

    data class MinLengthError(
        val minLength: Int
    ) : FieldError()

    data class DateFormatError(
        val dateFormat: String
    ) : FieldError()
}
