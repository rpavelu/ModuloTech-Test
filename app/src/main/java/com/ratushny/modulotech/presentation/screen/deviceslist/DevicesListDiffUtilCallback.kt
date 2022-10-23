package com.ratushny.modulotech.presentation.screen.deviceslist

import androidx.recyclerview.widget.DiffUtil
import com.ratushny.modulotech.domain.model.device.Device

class DevicesListDiffUtilCallback : DiffUtil.ItemCallback<Device>() {

    override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean =
        oldItem == newItem
}