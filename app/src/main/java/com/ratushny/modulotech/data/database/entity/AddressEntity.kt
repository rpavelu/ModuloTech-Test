package com.ratushny.modulotech.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ratushny.modulotech.data.database.DatabaseContract.Address.COLUMN_CITY
import com.ratushny.modulotech.data.database.DatabaseContract.Address.COLUMN_COUNTRY
import com.ratushny.modulotech.data.database.DatabaseContract.Address.COLUMN_ID
import com.ratushny.modulotech.data.database.DatabaseContract.Address.COLUMN_POSTAL_CODE
import com.ratushny.modulotech.data.database.DatabaseContract.Address.COLUMN_STREET
import com.ratushny.modulotech.data.database.DatabaseContract.Address.COLUMN_STREET_CODE
import com.ratushny.modulotech.data.database.DatabaseContract.Address.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class AddressEntity(
    @ColumnInfo(name = COLUMN_CITY)
    val city: String,

    @ColumnInfo(name = COLUMN_POSTAL_CODE)
    val postalCode: String,

    @ColumnInfo(name = COLUMN_STREET)
    val street: String,

    @ColumnInfo(name = COLUMN_STREET_CODE)
    val streetCode: String,

    @ColumnInfo(name = COLUMN_COUNTRY)
    val country: String,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0
}