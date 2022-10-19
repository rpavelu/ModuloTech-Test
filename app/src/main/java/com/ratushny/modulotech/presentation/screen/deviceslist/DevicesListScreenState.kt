package com.ratushny.modulotech.presentation.screen.deviceslist

import com.ratushny.modulotech.domain.model.device.Device
import com.ratushny.modulotech.domain.model.device.ProductType

data class DevicesListScreenState(
    val filteredDevices: List<Device>,
    val state: State,
    val filters: List<Filter>,
) {

    data class Filter(
        val productType: ProductType,
        val stringRes: Int,
        val state: Boolean,
    )

    enum class State {
        LOADING,
        SUCCESS,
        ERROR,
    }
}


