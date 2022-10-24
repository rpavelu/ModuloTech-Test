package com.ratushny.modulotech.presentation.screen.deviceslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ratushny.modulotech.R
import com.ratushny.modulotech.domain.interactor.DeviceInteractor
import com.ratushny.modulotech.domain.interactor.ModuloInteractor
import com.ratushny.modulotech.domain.model.device.Device
import com.ratushny.modulotech.domain.model.device.Device.Heater
import com.ratushny.modulotech.domain.model.device.Device.Light
import com.ratushny.modulotech.domain.model.device.Device.RollerShutter
import com.ratushny.modulotech.domain.model.device.ProductType
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

    init {
        _screenState.value = DevicesListScreenState(
            filteredDevices = emptyList(),
            state = DevicesListScreenState.State.LOADING,
            filters = listOf(
                DevicesListScreenState.Filter(
                    ProductType.HEATER,
                    R.string.heater,
                    true
                ),
                DevicesListScreenState.Filter(
                    ProductType.LIGHT,
                    R.string.light,
                    true
                ),
                DevicesListScreenState.Filter(
                    ProductType.ROLLERSHUTTER,
                    R.string.roller_shutter,
                    true
                ),
            )
        )
    }

    private var devices by Delegates.observable(emptyList<Device>()) { _, _, _ ->
        updateFilteredDevices()
    }

    private val _filtersDialogEvent = SingleLiveData<List<DevicesListScreenState.Filter>>()
    val filterDialogEvent: LiveData<List<DevicesListScreenState.Filter>>
        get() = _filtersDialogEvent

    override fun onAttached() {
        viewModelScope.launch {
            moduloInteractor.loadData(force = false)
            devices = deviceInteractor.loadDevices()
        }
    }

    private fun updateFilteredDevices() {
        _screenState.update {
            copy(
                filteredDevices = devices.filter { device -> device.passedFilter() },
                state = DevicesListScreenState.State.SUCCESS
            )
        }
    }

    fun handleFilterClicked() {
        _filtersDialogEvent.value = screenState.value?.filters ?: emptyList()
    }

    fun updateFilterState(productType: ProductType, enabled: Boolean) {
        _screenState.update {
            copy(filters = filters
                .map { filter ->
                    if (filter.productType == productType) {
                        filter.copy(isEnabled = enabled)
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
            _screenState.update {
                copy(filteredDevices = filteredDevices.filter { device -> device != removedDevice })
            }
        }
    }

    fun refreshData() {
        _screenState.update { copy(state = DevicesListScreenState.State.LOADING) }
        viewModelScope.launch {
            moduloInteractor.loadData(force = true)
            devices = deviceInteractor.loadDevices()
        }
    }

    private fun Device.passedFilter(): Boolean = screenState.value?.filters?.firstOrNull { filter ->
        filter.productType == when (this) {
            is Heater -> ProductType.HEATER
            is Light -> ProductType.LIGHT
            is RollerShutter -> ProductType.ROLLERSHUTTER
        }
    }?.isEnabled ?: false
}