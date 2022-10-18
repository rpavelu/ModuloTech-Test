package com.ratushny.modulotech.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ratushny.modulotech.data.database.entity.DeviceEntity

@Dao
abstract class DeviceDao : BaseDao<DeviceEntity>() {

    @Query(DeviceEntity.SELECT_ALL)
    abstract suspend fun selectAll(): List<DeviceEntity>

    @Query(DeviceEntity.DELETE_ALL)
    abstract suspend fun deleteAll()
}