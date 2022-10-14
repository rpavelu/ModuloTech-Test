package com.ratushny.modulotech.presentation.screen.deviceslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ratushny.modulotech.R
import com.ratushny.modulotech.data.model.Device

class DevicesListAdapter :
    RecyclerView.Adapter<DevicesListAdapter.ViewHolder>() {

    private var devicesItemData: List<Device> = emptyList()
//    private var filteredDevicesItemData: List<ModuloDevice> = emptyList()

//    lateinit var clickListener: ClickListener

//    fun setOnItemClickListener(clickListener: ClickListener) {
//        this.clickListener = clickListener
//    }

//    interface ClickListener {
//        fun onClick(pos: Int, view: View)
//    }

    fun addItemList(itemList: List<Device>) {
        devicesItemData = itemList
//        filteredDevicesItemData = itemList

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.device_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = devicesItemData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device: Device = devicesItemData[position]
        holder.nameTextView.text = device.deviceName

        val deviceType = device.productType
        holder.deviceImageView.setImageResource(
            when (deviceType) {
                "Light" -> R.drawable.ic_baseline_bulb_24
                "Heater" -> R.drawable.ic_baseline_whatshot_24
                "RollerShutter" -> R.drawable.ic_baseline_dehaze_24
                else -> R.drawable.ic_baseline_wb_sunny_24
            }
        )
    }

    inner class ViewHolder(
        itemView: View,
        val nameTextView: TextView = itemView.findViewById(R.id.device_name),
        val deviceImageView: ImageView = itemView.findViewById(R.id.device_image)
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View) {
//            clickListener.onClick(bindingAdapterPosition, v)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}