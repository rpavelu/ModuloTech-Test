package com.ratushny.modulotech.domain.interactor

import com.ratushny.modulotech.domain.api.UserRepositoryApi
import com.ratushny.modulotech.domain.model.user.User

class UserInteractor(
    private val repository: UserRepositoryApi
) {

    suspend fun loadUser(): User = repository.loadUser()

    suspend fun updateUser(user: User) = repository.updateUser(user)
}