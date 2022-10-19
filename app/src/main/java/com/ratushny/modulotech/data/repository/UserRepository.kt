package com.ratushny.modulotech.data.repository

import com.ratushny.modulotech.data.database.dao.UserDao
import com.ratushny.modulotech.data.database.mapper.convertToAppEntity
import com.ratushny.modulotech.data.database.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.domain.api.UserRepositoryApi
import com.ratushny.modulotech.domain.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao
) : UserRepositoryApi {

    override suspend fun loadUser(): User = withContext(Dispatchers.IO) {
        userDao.selectUser().convertToAppEntity()
    }

    override suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        userDao.update(user.convertToDatabaseEntity())
    }
}