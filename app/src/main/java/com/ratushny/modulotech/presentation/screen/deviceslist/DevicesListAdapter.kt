package com.ratushny.modulotech.presentation.screen.deviceslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.ItemDeviceBinding
import com.ratushny.modulotech.domain.model.device.Device
import com.ratushny.modulotech.domain.model.device.Device.Heater
import com.ratushny.modulotech.domain.model.device.Device.Light
import com.ratushny.modulotech.domain.model.device.Device.RollerShutter

class DevicesListAdapter(
    private val onClick: (device: Device) -> Unit
) : ListAdapter<Device, DevicesListAdapter.ViewHolder>(DevicesListDiffUtilCallback()) {

    fun getDeviceByPosition(position: Int): Device = getItem(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    inner class ViewHolder(
        private val parent: ViewGroup,
        private val binding: ItemDeviceBinding = ItemDeviceBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            device: Device,
            onClick: (device: Device) -> Unit
        ) = with(binding) {
            textDeviceName.text = device.deviceName

            imageDevice.setImageResource(
                when (device) {
                    is Light -> R.drawable.ic_bulb
                    is Heater -> R.drawable.ic_heater
                    is RollerShutter -> R.drawable.ic_roller_shutter
                }
            )

            textDeviceValue.text = when (device) {
                is Light -> device.intensity.toString()
                is Heater -> itemView.context.getString(
                    R.string.format_heater_temperature,
                    device.temperature
                )
                is RollerShutter -> device.position.toString()
            }

            itemView.setOnClickListener { onClick(device) }
        }
    }
}