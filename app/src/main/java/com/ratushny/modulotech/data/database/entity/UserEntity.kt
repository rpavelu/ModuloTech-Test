package com.ratushny.modulotech.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @ColumnInfo(name = COLUMN_FIRST_NAME)
    val firstName: String?,

    @ColumnInfo(name = COLUMN_LAST_NAME)
    val lastName: String?,

    @Embedded val address: AddressEntity?,

    @ColumnInfo(name = COLUMN_DATE)
    val birthdate: Date?,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0

    companion object {
        const val TABLE_NAME = "user"

        const val SELECT_ALL = "select * from $TABLE_NAME"
        const val DELETE_ALL = "delete from $TABLE_NAME"

        private const val COLUMN_ID = "id"
        private const val COLUMN_FIRST_NAME = "first_name"
        private const val COLUMN_LAST_NAME = "last_name"
        private const val COLUMN_DATE = "date"
    }
}