package com.ratushny.modulotech.presentation.screen.deviceslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ratushny.modulotech.R
import com.ratushny.modulotech.data.network.model.DeviceResponse
import com.ratushny.modulotech.data.network.model.ProductTypeResponse
import com.ratushny.modulotech.databinding.DeviceItemBinding

class DevicesListAdapter(
    private val onClick: (device: DeviceResponse) -> Unit
) : RecyclerView.Adapter<DevicesListAdapter.ViewHolder>() {

    private var devices: List<DeviceResponse> = emptyList()

    fun updateDevicesList(list: List<DeviceResponse>) {
        val diffCallback = DevicesListDiffUtilCallback(devices, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

        this.devices = list
    }

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
            device: DeviceResponse,
            onClick: (device: DeviceResponse) -> Unit
        ) = with(binding) {
            deviceName.text = device.deviceName

            val deviceType = device.productType
            deviceImage.setImageResource(
                when (deviceType) {
                    ProductTypeResponse.Light -> R.drawable.ic_baseline_bulb_24
                    ProductTypeResponse.Heater -> R.drawable.ic_baseline_whatshot_24
                    ProductTypeResponse.RollerShutter -> R.drawable.ic_baseline_dehaze_24
                }
            )

            itemView.setOnClickListener { onClick(device) }
        }
    }
}