package com.ratushny.modulotech.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ratushny.modulotech.data.database.DatabaseContract.Device.DELETE_ALL
import com.ratushny.modulotech.data.database.DatabaseContract.Device.SELECT_ALL
import com.ratushny.modulotech.data.database.entity.DeviceEntity

@Dao
interface DeviceDao : BaseDao<DeviceEntity> {

    @Query(SELECT_ALL)
    suspend fun selectAll(): List<DeviceEntity>

    @Query(DELETE_ALL)
    suspend fun deleteAll()
}