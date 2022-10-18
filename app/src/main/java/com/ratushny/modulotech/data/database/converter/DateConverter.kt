package com.ratushny.modulotech.data.database.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun dateToLong(date: Date?): Long? = date?.time

    @TypeConverter
    @JvmStatic
    fun longToDate(millis: Long?): Date? = millis?.let { Date(it) }
}