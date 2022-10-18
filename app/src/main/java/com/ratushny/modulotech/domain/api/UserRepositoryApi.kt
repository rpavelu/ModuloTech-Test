package com.ratushny.modulotech.domain.api

import com.ratushny.modulotech.domain.entity.user.User

interface UserRepositoryApi {

    suspend fun loadUser(): User

    suspend fun updateUser(user: User)
}