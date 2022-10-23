package com.ratushny.modulotech.data.database.mapper

import com.ratushny.modulotech.data.database.entity.DeviceEntity
import com.ratushny.modulotech.domain.model.device.Device
import com.ratushny.modulotech.domain.model.device.Device.Heater
import com.ratushny.modulotech.domain.model.device.Device.Light
import com.ratushny.modulotech.domain.model.device.Device.RollerShutter
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.domain.model.device.ProductType

fun DeviceEntity.convertToAppEntity(): Device = when (productType) {
    ProductType.LIGHT -> Light(
        id = this.id,
        deviceName = this.deviceName,
        intensity = this.intensity,
        mode = this.mode,
    )
    ProductType.HEATER -> Heater(
        id = this.id,
        deviceName = this.deviceName,
        mode = this.mode,
        temperature = this.temperature,
    )
    ProductType.ROLLERSHUTTER -> RollerShutter(
        id = this.id,
        deviceName = this.deviceName,
        position = this.position,
    )
}

fun Device.convertToDatabaseEntity(): DeviceEntity = DeviceEntity(
    id = id,
    productType = when (this) {
        is Light -> ProductType.LIGHT
        is Heater -> ProductType.HEATER
        is RollerShutter -> ProductType.ROLLERSHUTTER
    },
    deviceName = deviceName,
    intensity = if (this is Light) this.intensity else 0,
    mode = when (this) {
        is Light -> this.mode
        is Heater -> this.mode
        else -> DeviceMode.NOT_USED
    },
    position = if (this is RollerShutter) this.position else 0,
    temperature = if (this is Heater) this.temperature else 0.0f,
)

