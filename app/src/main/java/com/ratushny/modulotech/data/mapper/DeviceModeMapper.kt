package com.ratushny.modulotech.data.mapper

import com.ratushny.modulotech.data.network.model.DeviceModeResponse
import com.ratushny.modulotech.domain.entity.device.DeviceMode

class DeviceModeMapper {
    fun convert(response: DeviceModeResponse): DeviceMode = when (response) {
        DeviceModeResponse.OFF -> DeviceMode.OFF
        else -> DeviceMode.ON
    }
}