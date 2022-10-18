package com.ratushny.modulotech.data.database

import android.content.Context
import androidx.room.Room
import com.ratushny.modulotech.data.database.dao.DeviceDao
import com.ratushny.modulotech.data.database.dao.UserDao

class DatabaseProvider(context: Context) {
    private val database = Room.databaseBuilder(
        context,
        Database::class.java,
        "main_database"
    ).build()

    val deviceDao: DeviceDao
        get() = database.deviceDao()

    val userDao: UserDao
        get() = database.userDao()
}