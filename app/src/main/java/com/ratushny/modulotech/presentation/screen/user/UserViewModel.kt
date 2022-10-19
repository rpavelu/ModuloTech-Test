package com.ratushny.modulotech.presentation.screen.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.domain.interactor.UserInteractor
import com.ratushny.modulotech.domain.model.user.Address
import com.ratushny.modulotech.domain.model.user.User
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.util.Date

class UserViewModel(
    private val userInteractor: UserInteractor
) : BaseViewModel<UserScreenState>() {

    private var userId: Long = -1

    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String>
        get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String>
        get() = _lastName

    private val _birthdate = MutableLiveData<Date>()
    val birthdate: LiveData<Date>
        get() = _birthdate

    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city

    private val _postalCode = MutableLiveData<Int>()
    val postalCode: LiveData<Int>
        get() = _postalCode

    private val _street = MutableLiveData<String>()
    val street: LiveData<String>
        get() = _street

    private val _streetCode = MutableLiveData<String>()
    val streetCode: LiveData<String>
        get() = _streetCode

    private val _country = MutableLiveData<String>()
    val country: LiveData<String>
        get() = _country

    fun updateFirstName(text: String) {
        _firstName.value = text
    }

    fun updateLastName(text: String) {
        _lastName.value = text
    }

    fun updateBirthdate(text: String) {
        val date = DateFormat.getDateInstance(DateFormat.SHORT).parse(text)
        _birthdate.value = date
    }

    fun updateCity(text: String) {
        _city.value = text
    }

    fun updatePostalCode(text: String) {
        _postalCode.value = text.toInt()
    }

    fun updateStreet(text: String) {
        _street.value = text
    }

    fun updateStreetCode(text: String) {
        _streetCode.value = text
    }

    fun updateCountry(text: String) {
        _country.value = text
    }

    fun refreshUserData() {
        viewModelScope.launch {
            try {
                val userData = userInteractor.loadUser()

                userId = userData.id

                _firstName.value = userData.firstName
                _lastName.value = userData.lastName
                _birthdate.value = userData.birthDate

                val address = userData.address
                _city.value = address?.city
                _postalCode.value = address?.postalCode
                _street.value = address?.street
                _streetCode.value = address?.streetCode
                _country.value = address?.country

            } catch (error: Throwable) {
                Timber.e("Error loading user data: $error")
            }
        }
    }

    fun updateUserData() {
        viewModelScope.launch {
            userInteractor.updateUser(
                User(
                    id = userId,
                    firstName = _firstName.value ?: "",
                    lastName = _lastName.value ?: "",
                    address = Address(
                        city = _city.value ?: "",
                        postalCode = _postalCode.value ?: 0,
                        street = _street.value ?: "",
                        streetCode = _streetCode.value ?: "",
                        country = _country.value ?: ""
                    ),
                    birthDate = _birthdate.value ?: Date()
                )
            )
        }
    }

    override fun createInitialState(): UserScreenState {
        return UserScreenState()
    }

    override fun onAttached() {
        //TODO
    }
}