package com.ratushny.modulotech.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ratushny.modulotech.data.database.converter.DateConverter
import com.ratushny.modulotech.data.database.dao.DeviceDao
import com.ratushny.modulotech.data.database.dao.UserDao
import com.ratushny.modulotech.data.database.entity.AddressEntity
import com.ratushny.modulotech.data.database.entity.DeviceEntity
import com.ratushny.modulotech.data.database.entity.UserEntity

@Database(
    entities = [
        DeviceEntity::class,
        UserEntity::class,
        AddressEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
    abstract fun userDao(): UserDao
}