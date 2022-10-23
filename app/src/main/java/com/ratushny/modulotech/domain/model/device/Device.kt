package com.ratushny.modulotech.domain.model.device

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Device(
    open val id: Long,
    open val deviceName: String,
) : Parcelable {

    data class Light(
        override val id: Long,
        override val deviceName: String,
        val mode: DeviceMode,
        val intensity: Int,
    ) : Device(id, deviceName)

    data class Heater(
        override val id: Long,
        override val deviceName: String,
        val mode: DeviceMode,
        val temperature: Float,
    ) : Device(id, deviceName)

    data class RollerShutter(
        override val id: Long,
        override val deviceName: String,
        val position: Int,
    ) : Device(id, deviceName)
}