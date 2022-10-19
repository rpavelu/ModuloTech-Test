package com.ratushny.modulotech.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ratushny.modulotech.data.database.DatabaseContract.User.DELETE_ALL
import com.ratushny.modulotech.data.database.DatabaseContract.User.SELECT_USER
import com.ratushny.modulotech.data.database.entity.UserEntity

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query(SELECT_USER)
    suspend fun selectUser(): UserEntity

    @Query(DELETE_ALL)
    suspend fun deleteAll()
}