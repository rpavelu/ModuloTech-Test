package com.ratushny.modulotech.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ratushny.modulotech.data.database.DatabaseContract.Device.COLUMN_DEVICE_NAME
import com.ratushny.modulotech.data.database.DatabaseContract.Device.COLUMN_ID
import com.ratushny.modulotech.data.database.DatabaseContract.Device.COLUMN_INTENSITY
import com.ratushny.modulotech.data.database.DatabaseContract.Device.COLUMN_MODE
import com.ratushny.modulotech.data.database.DatabaseContract.Device.COLUMN_POSITION
import com.ratushny.modulotech.data.database.DatabaseContract.Device.COLUMN_PRODUCT_TYPE
import com.ratushny.modulotech.data.database.DatabaseContract.Device.COLUMN_TEMPERATURE
import com.ratushny.modulotech.data.database.DatabaseContract.Device.TABLE_NAME
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.domain.model.device.ProductType

@Entity(tableName = TABLE_NAME)
data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = COLUMN_PRODUCT_TYPE)
    val productType: ProductType,

    @ColumnInfo(name = COLUMN_DEVICE_NAME)
    val deviceName: String,

    @ColumnInfo(name = COLUMN_MODE)
    val mode: DeviceMode,

    @ColumnInfo(name = COLUMN_INTENSITY)
    val intensity: Int,

    @ColumnInfo(name = COLUMN_POSITION)
    val position: Int,

    @ColumnInfo(name = COLUMN_TEMPERATURE)
    val temperature: Float,
)