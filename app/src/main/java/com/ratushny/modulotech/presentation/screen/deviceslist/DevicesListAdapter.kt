package com.ratushny.modulotech.presentation.screen.deviceslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.ItemDeviceBinding
import com.ratushny.modulotech.domain.model.device.Device
import com.ratushny.modulotech.domain.model.device.Heater
import com.ratushny.modulotech.domain.model.device.Light
import com.ratushny.modulotech.domain.model.device.RollerShutter

class DevicesListAdapter(
    private val onClick: (device: Device) -> Unit
) : RecyclerView.Adapter<DevicesListAdapter.ViewHolder>() {

    private var devices: List<Device> = emptyList()

    fun updateDevicesList(list: List<Device>) {
        val diffCallback = DevicesListDiffUtilCallback(devices, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

        this.devices = list
    }

    fun getDeviceByPosition(position: Int): Device = devices[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = devices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(devices[position], onClick)
    }

    inner class ViewHolder(
        private val binding: ItemDeviceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            device: Device,
            onClick: (device: Device) -> Unit
        ) = with(binding) {
            deviceName.text = device.deviceName

            deviceImage.setImageResource(
                when (device) {
                    is Light -> R.drawable.ic_bulb
                    is Heater -> R.drawable.ic_heater
                    is RollerShutter -> R.drawable.ic_roller_shutter
                }
            )

            deviceValue.text = when (device) {
                is Light -> device.intensity.toString()
                is Heater -> device.temperature.toString()
                is RollerShutter -> device.position.toString()
            }

            itemView.setOnClickListener { onClick(device) }
        }
    }
}