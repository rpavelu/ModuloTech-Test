package com.ratushny.modulotech.data.network.mapper

import com.ratushny.modulotech.data.network.model.DeviceModeResponse
import com.ratushny.modulotech.data.network.model.DeviceResponse
import com.ratushny.modulotech.data.network.model.ProductTypeResponse
import com.ratushny.modulotech.domain.model.device.Device
import com.ratushny.modulotech.domain.model.device.Device.Heater
import com.ratushny.modulotech.domain.model.device.Device.Light
import com.ratushny.modulotech.domain.model.device.Device.RollerShutter
import com.ratushny.modulotech.domain.model.device.DeviceMode

fun DeviceResponse.convertToAppEntity(): Device? = productType
    ?.let { productType ->
        when (productType) {
            ProductTypeResponse.Light -> Light(
                id = id ?: 0,
                deviceName = deviceName ?: "",
                mode = mode?.convertToAppEntity() ?: DeviceMode.OFF,
                intensity = intensity ?: 0,
            )
            ProductTypeResponse.Heater -> Heater(
                id = id ?: 0,
                deviceName = deviceName ?: "",
                mode = mode?.convertToAppEntity() ?: DeviceMode.OFF,
                temperature = temperature ?: 0.0f,
            )
            ProductTypeResponse.RollerShutter -> RollerShutter(
                id = id ?: 0,
                deviceName = deviceName ?: "",
                position = position ?: 0,
            )
        }
    }

fun DeviceModeResponse.convertToAppEntity(): DeviceMode = when (this) {
    DeviceModeResponse.OFF -> DeviceMode.OFF
    DeviceModeResponse.ON -> DeviceMode.ON
}