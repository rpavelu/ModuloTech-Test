package com.ratushny.modulotech.data.database.mapper

import com.ratushny.modulotech.data.database.entity.DeviceEntity
import com.ratushny.modulotech.domain.entity.device.*

fun DeviceEntity.convertToAppEntity(): Device = when (productType) {
    ProductType.LIGHT -> Light(
        id = this.id,
        deviceName = this.deviceName ?: "",
        intensity = this.intensity ?: 0,
        mode = this.mode ?: DeviceMode.OFF
    )
    ProductType.HEATER -> Heater(
        id = this.id,
        deviceName = this.deviceName ?: "",
        mode = this.mode ?: DeviceMode.OFF,
        temperature = this.temperature ?: 0.0f
    )
    ProductType.ROLLERSHUTTER -> RollerShutter(
        id = this.id,
        deviceName = this.deviceName ?: "",
        position = this.position ?: 0
    )
}

fun Device.convertToDatabaseEntity(): DeviceEntity = DeviceEntity(
    id = id,
    productType = productType,
    deviceName = deviceName,
    intensity = if (this is Light) this.intensity else 0,
    mode = when (this) {
        is Light -> this.mode
        is Heater -> this.mode
        else -> null
    },
    position = if (this is RollerShutter) this.position else 0,
    temperature = if (this is Heater) this.temperature else 0.0f,
)

