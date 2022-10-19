package com.ratushny.modulotech.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ratushny.modulotech.data.database.DatabaseContract.User.COLUMN_DATE
import com.ratushny.modulotech.data.database.DatabaseContract.User.COLUMN_FIRST_NAME
import com.ratushny.modulotech.data.database.DatabaseContract.User.COLUMN_ID
import com.ratushny.modulotech.data.database.DatabaseContract.User.COLUMN_LAST_NAME
import com.ratushny.modulotech.data.database.DatabaseContract.User.TABLE_NAME
import java.util.Date

@Entity(tableName = TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0,

    @ColumnInfo(name = COLUMN_FIRST_NAME)
    val firstName: String,

    @ColumnInfo(name = COLUMN_LAST_NAME)
    val lastName: String,

    @Embedded val address: AddressEntity,

    @ColumnInfo(name = COLUMN_DATE)
    val birthdate: Date,
)