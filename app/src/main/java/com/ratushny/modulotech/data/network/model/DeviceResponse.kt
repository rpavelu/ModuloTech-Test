package com.ratushny.modulotech.data.network.model

data class DeviceResponse(
    val id: Int,
    val deviceName: String,
    val intensity: Int,
    val mode: String,
    val temperature: Int,
    val position: Int,
    val productType: ProductTypeResponse
) : java.io.Serializable {

    override fun hashCode(): Int =
        31 * deviceName.hashCode() + id.hashCode()
}