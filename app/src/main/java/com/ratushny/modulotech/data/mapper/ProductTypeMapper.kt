package com.ratushny.modulotech.data.mapper

import com.ratushny.modulotech.data.network.model.ProductTypeResponse
import com.ratushny.modulotech.domain.entity.device.ProductType

class ProductTypeMapper {
    fun convert(response: ProductTypeResponse): ProductType = when (response) {
        ProductTypeResponse.Light -> ProductType.LIGHT
        ProductTypeResponse.Heater -> ProductType.HEATER
        ProductTypeResponse.RollerShutter -> ProductType.ROLLERSHUTTER
    }
}