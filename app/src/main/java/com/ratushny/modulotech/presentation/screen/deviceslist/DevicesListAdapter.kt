package com.ratushny.modulotech.presentation.screen.deviceslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.DeviceItemBinding
import com.ratushny.modulotech.domain.entity.device.Device
import com.ratushny.modulotech.domain.entity.device.ProductType

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
        val binding = DeviceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = devices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(devices[position], onClick)
    }

    inner class ViewHolder(
        private val binding: DeviceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            device: Device,
            onClick: (device: Device) -> Unit
        ) = with(binding) {
            deviceName.text = device.deviceName

            val deviceType = device.productType
            deviceImage.setImageResource(
                when (deviceType) {
                    ProductType.LIGHT -> R.drawable.ic_baseline_bulb_24
                    ProductType.HEATER -> R.drawable.ic_baseline_whatshot_24
                    ProductType.ROLLERSHUTTER -> R.drawable.ic_baseline_dehaze_24
                }
            )

            itemView.setOnClickListener { onClick(device) }
        }
    }
}