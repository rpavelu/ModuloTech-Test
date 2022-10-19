package com.ratushny.modulotech.presentation.screen.deviceslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.R
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.interactor.ModuloInteractor
import com.ratushny.modulotech.domain.model.device.Device
import com.ratushny.modulotech.domain.model.device.Heater
import com.ratushny.modulotech.domain.model.device.Light
import com.ratushny.modulotech.domain.model.device.ProductType
import com.ratushny.modulotech.domain.model.device.RollerShutter
import com.ratushny.modulotech.presentation.common.SingleLiveData
import com.ratushny.modulotech.presentation.extensions.update
import com.ratushny.modulotech.presentation.screen.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class DevicesListViewModel(
    private val moduloInteractor: ModuloInteractor,
    private val deviceInteractor: DeviceInteractor,
) : BaseViewModel<DevicesListScreenState>() {

    private var devices by Delegates.observable(emptyList<Device>()) { _, _, _ ->
        updateFilteredDevices()
    }

    private val _filtersDialogState = SingleLiveData<List<DevicesListScreenState.Filter>>()
    val filterDialogState: LiveData<List<DevicesListScreenState.Filter>>
        get() = _filtersDialogState

    override fun createInitialState(): DevicesListScreenState {
        return DevicesListScreenState(
            filteredDevices = emptyList(),
            isLoading = true,
            filters = listOf(
                DevicesListScreenState.Filter(ProductType.HEATER, R.string.heater, true),
                DevicesListScreenState.Filter(ProductType.LIGHT, R.string.light, true),
                DevicesListScreenState.Filter(
                    ProductType.ROLLERSHUTTER,
                    R.string.roller_shutter,
                    true
                ),
            )
        )
    }

    override fun onAttached() {
        viewModelScope.launch {
            moduloInteractor.loadData(force = false)
            devices = deviceInteractor.loadDevices()
        }
    }

    private fun updateFilteredDevices() {
        screenStateMutable.update {
            it.copy(
                filteredDevices = devices.filter { device ->
                    device.passedFilter()
                },
                isLoading = false
            )
        }
    }

    fun handleFilterClicked() {
        _filtersDialogState.value = screenState.value?.filters ?: emptyList()
    }

    fun updateFilterState(productType: ProductType, enabled: Boolean) {
        screenStateMutable.update {
            it.copy(filters = it.filters.map { filter ->
                if (filter.productType == productType) {
                    filter.copy(state = enabled)
                } else {
                    filter
                }
            })
        }
        updateFilteredDevices()
    }

    fun removeDevice(removedDevice: Device) {
        viewModelScope.launch(Dispatchers.Main) {
            deviceInteractor.deleteDevice(removedDevice)
            screenStateMutable.update {
                it.copy(filteredDevices = it.filteredDevices.filter { device ->
                    device != removedDevice
                })
            }
        }
    }

    fun refreshData() {
        screenStateMutable.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            moduloInteractor.loadData(force = true)
            devices = deviceInteractor.loadDevices()
        }
    }

    private fun Device.passedFilter(): Boolean {
        return screenState.value?.filters?.firstOrNull { filter ->
            when (this) {
                is Heater -> filter.productType == ProductType.HEATER
                is Light -> filter.productType == ProductType.LIGHT
                is RollerShutter -> filter.productType == ProductType.ROLLERSHUTTER
            }
        }?.state ?: false
    }

    private fun List<DevicesListScreenState.Filter>.getForProductType(productType: ProductType): DevicesListScreenState.Filter? {
        return firstOrNull { it.productType == productType }
    }

}