package com.ratushny.modulotech.presentation.screen.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.UserInteractor
import com.ratushny.modulotech.domain.model.user.Address
import com.ratushny.modulotech.domain.model.user.User
import com.ratushny.modulotech.presentation.common.SingleLiveData
import com.ratushny.modulotech.presentation.extensions.update
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.properties.Delegates

class UserViewModel(
    private val userInteractor: UserInteractor
) : BaseViewModel<UserScreenState>() {

    init {
        _screenState.value = UserScreenState()
    }

    private var user: User? by Delegates.observable(null) { _, _, newUser ->
        newUser ?: return@observable
        _screenState.update {
            UserScreenState(
                firstName = UserScreenState.Field(newUser.firstName),
                lastName = UserScreenState.Field(newUser.lastName),
                birthDate = UserScreenState.Field(dateFormat.format(newUser.birthDate)),
                city = UserScreenState.Field(newUser.address.city),
                postalCode = UserScreenState.Field(newUser.address.postalCode),
                street = UserScreenState.Field(newUser.address.street),
                streetCode = UserScreenState.Field(newUser.address.streetCode),
                country = UserScreenState.Field(newUser.address.country)
            )
        }
    }

    private val _savedEvent = SingleLiveData<Any>()
    val savedEvent: LiveData<Any>
        get() = _savedEvent

    private val dateFormat: SimpleDateFormat
        get() = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    override fun onAttached() {
        viewModelScope.launch {
            user = userInteractor.loadUser()
        }
    }

    fun updateFirstName(text: String) {
        _screenState.update { copy(firstName = UserScreenState.Field(text)) }
    }

    fun updateLastName(text: String) {
        _screenState.update { copy(lastName = UserScreenState.Field(text)) }
    }

    fun updateBirthdate(text: String) {
        _screenState.update { copy(birthDate = UserScreenState.Field(text)) }
    }

    fun updateCity(text: String) {
        _screenState.update { copy(city = UserScreenState.Field(text)) }
    }

    fun updatePostalCode(text: String) {
        _screenState.update { copy(postalCode = UserScreenState.Field(text)) }
    }

    fun updateStreet(text: String) {
        _screenState.update { copy(street = UserScreenState.Field(text)) }
    }

    fun updateStreetCode(text: String) {
        _screenState.update { copy(streetCode = UserScreenState.Field(text)) }
    }

    fun updateCountry(text: String) {
        _screenState.update { copy(country = UserScreenState.Field(text)) }
    }

    fun handleSaveClicked() {
        validateUserData {
            viewModelScope.launch {
                val screenState = screenState.value ?: return@launch
                user?.copy(
                    firstName = screenState.firstName.value,
                    lastName = screenState.lastName.value,
                    birthDate = dateFormat.parse(screenState.birthDate.value) ?: user!!.birthDate,
                    address = Address(
                        city = screenState.city.value,
                        postalCode = screenState.postalCode.value,
                        street = screenState.street.value,
                        streetCode = screenState.streetCode.value,
                        country = screenState.country.value
                    )
                )?.let {
                    userInteractor.updateUser(it)
                    _savedEvent.value = Any()
                }
            }
        }
    }

    private fun validateUserData(onSuccess: () -> Unit) {
        val currentState = _screenState.value ?: return
        currentState.allFields.forEach {
            it.validateEmpty()
        }
        currentState.postalCode.validateMinLength(POSTAL_CODE_MIN_LENGTH)
        currentState.birthDate.validateDateFormat(dateFormat)
        _screenState.update { currentState.copy() }
        if (currentState.allFields.none { it.error != null }) {
            onSuccess()
        }
    }

    private fun UserScreenState.Field.validateEmpty() {
        if (value.isEmpty()) {
            error = UserScreenState.EmptyFieldError
        }
    }

    private fun UserScreenState.Field.validateMinLength(minLength: Int) {
        if (value.length < minLength) {
            error = UserScreenState.MinLengthError(minLength)
        }
    }

    private fun UserScreenState.Field.validateDateFormat(dateFormat: SimpleDateFormat) {
        try {
            val parsedDate = dateFormat.parse(value)
                ?: run {
                    error = UserScreenState.DateFormatError(dateFormat.toPattern())
                    return
                }
            val formattedDate = dateFormat.format(parsedDate)
            if (formattedDate != value) {
                error = UserScreenState.DateFormatError(dateFormat.toPattern())
            }
        } catch (e: Throwable) {
            error = UserScreenState.DateFormatError(dateFormat.toPattern())
        }
    }

    companion object {
        private const val POSTAL_CODE_MIN_LENGTH = 5
        private const val DATE_FORMAT = "dd/MM/yyyy"
    }
}