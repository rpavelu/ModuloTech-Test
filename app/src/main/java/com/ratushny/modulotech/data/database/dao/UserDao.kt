package com.ratushny.modulotech.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ratushny.modulotech.data.database.entity.UserEntity

@Dao
abstract class UserDao : BaseDao<UserEntity>() {

    @Query(UserEntity.SELECT_ALL)
    abstract suspend fun selectAll(): List<UserEntity>

    @Query(UserEntity.DELETE_ALL)
    abstract suspend fun deleteAll()
}