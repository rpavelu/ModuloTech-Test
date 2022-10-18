package com.ratushny.modulotech.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(obj: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(obj: List<T>)

    @Delete
    abstract suspend fun delete(obj: T)

    @Delete
    abstract suspend fun delete(obj: List<T>)
}