package com.ratushny.modulotech.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ratushny.modulotech.domain.entity.device.DeviceMode
import com.ratushny.modulotech.domain.entity.device.ProductType

@Entity(tableName = DeviceEntity.TABLE_NAME)
data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = COLUMN_PRODUCT_TYPE)
    val productType: ProductType,

    @ColumnInfo(name = COLUMN_DEVICE_NAME)
    val deviceName: String?,

    @ColumnInfo(name = COLUMN_MODE)
    val mode: DeviceMode?,

    @ColumnInfo(name = COLUMN_INTENSITY)
    val intensity: Int?,

    @ColumnInfo(name = COLUMN_POSITION)
    val position: Int?,

    @ColumnInfo(name = COLUMN_TEMPERATURE)
    val temperature: Float?,
) {
    companion object {
        const val TABLE_NAME = "devices"

        const val SELECT_ALL = "select * from $TABLE_NAME"
        const val DELETE_ALL = "delete from $TABLE_NAME"

        private const val COLUMN_ID = "id"
        private const val COLUMN_PRODUCT_TYPE = "product_type"
        private const val COLUMN_DEVICE_NAME = "device_name"
        private const val COLUMN_MODE = "mode"
        private const val COLUMN_INTENSITY = "intensity"
        private const val COLUMN_POSITION = "position"
        private const val COLUMN_TEMPERATURE = "temperature"
    }
}