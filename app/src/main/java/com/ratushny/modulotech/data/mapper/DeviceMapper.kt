package com.ratushny.modulotech.data.mapper

import com.ratushny.modulotech.data.network.model.DeviceResponse
import com.ratushny.modulotech.data.network.model.ProductTypeResponse
import com.ratushny.modulotech.domain.entity.device.Device
import com.ratushny.modulotech.domain.entity.device.Heater
import com.ratushny.modulotech.domain.entity.device.Light
import com.ratushny.modulotech.domain.entity.device.RollerShutter

fun DeviceResponse.convertToAppEntity(): Device = when (productType) {
    ProductTypeResponse.Light -> Light(
        id = id,
        deviceName = deviceName,
        mode = DeviceModeMapper().convert(mode),
        intensity = intensity,
    )
    ProductTypeResponse.Heater -> Heater(
        id = id,
        deviceName = deviceName,
        mode = DeviceModeMapper().convert(mode),
        temperature = temperature,
    )
    ProductTypeResponse.RollerShutter -> RollerShutter(
        id = id,
        deviceName = deviceName,
        position = position,
    )
}