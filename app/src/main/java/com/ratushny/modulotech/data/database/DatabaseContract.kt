package com.ratushny.modulotech.data.database

object DatabaseContract {
    object Device {
        const val TABLE_NAME = "devices"

        const val COLUMN_ID = "id"
        const val COLUMN_PRODUCT_TYPE = "product_type"
        const val COLUMN_DEVICE_NAME = "device_name"
        const val COLUMN_MODE = "mode"
        const val COLUMN_INTENSITY = "intensity"
        const val COLUMN_POSITION = "position"
        const val COLUMN_TEMPERATURE = "temperature"

        const val SELECT_ALL = "SELECT * FROM $TABLE_NAME"
        const val DELETE_ALL = "DELETE FROM $TABLE_NAME"
    }

    object User {
        const val TABLE_NAME = "user"

        const val COLUMN_ID = "id"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_DATE = "date"

        const val SELECT_USER = "SELECT * FROM $TABLE_NAME LIMIT 1"
        const val DELETE_ALL = "DELETE FROM $TABLE_NAME"
    }

    object Address {
        const val TABLE_NAME = "address"

        const val COLUMN_ID = "address_id"
        const val COLUMN_CITY = "city"
        const val COLUMN_POSTAL_CODE = "postal_code"
        const val COLUMN_STREET = "street"
        const val COLUMN_STREET_CODE = "street_code"
        const val COLUMN_COUNTRY = "country"
    }
}