package com.ratushny.modulotech.presentation.screen.deviceslist

import androidx.recyclerview.widget.DiffUtil
import com.ratushny.modulotech.data.network.model.DeviceResponse

open class DevicesListDiffUtilCallback(
    private val oldDevices: List<DeviceResponse>,
    private val newDevices: List<DeviceResponse>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldDevices.size

    override fun getNewListSize(): Int = newDevices.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldDevices[oldItemPosition].id == newDevices[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldDevices[oldItemPosition] == newDevices[newItemPosition]
}