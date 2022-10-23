package com.ratushny.modulotech.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ratushny.modulotech.data.database.DatabaseContract.Device.DELETE_ALL
import com.ratushny.modulotech.data.database.DatabaseContract.Device.IS_EMPTY
import com.ratushny.modulotech.data.database.DatabaseContract.Device.SELECT_ALL
import com.ratushny.modulotech.data.database.entity.DeviceEntity

@Dao
interface DeviceDao : BaseDao<DeviceEntity> {

    @Query(SELECT_ALL)
    suspend fun selectAll(): List<DeviceEntity>

    @Query(DELETE_ALL)
    suspend fun deleteAll()

    @Query(IS_EMPTY)
    suspend fun isEmpty(): Boolean
}