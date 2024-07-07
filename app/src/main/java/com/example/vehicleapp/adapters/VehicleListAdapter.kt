package com.example.vehicleapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicleapp.R
import com.example.vehicleapp.databinding.ItemVehicleLayoutBinding
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.utils.GenericListeners
import com.example.vehicleapp.viewholder.VehicleViewHolder

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class VehicleListAdapter(private val clickListener: GenericListeners) :
    RecyclerView.Adapter<VehicleViewHolder>() {

    var vehicleItems: ArrayList<VehicleAttendance> = ArrayList()
        set(value) {
            field = value
            val diffCallback =
                VehicleViewHolder.ChildViewDiffUtils(filteredVehicleItems, vehicleItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            if (filteredVehicleItems.isNotEmpty())
                filteredVehicleItems.clear()
            filteredVehicleItems.addAll(value)
        }

    private var filteredVehicleItems: ArrayList<VehicleAttendance> = ArrayList()
        set(value) {
            field = value
            val diffCallback =
                VehicleViewHolder.ChildViewDiffUtils(filteredVehicleItems, vehicleItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    fun clearVehicleItems() {
        vehicleItems.clear()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VehicleViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_vehicle_layout, viewGroup, false)
        val binding = ItemVehicleLayoutBinding.bind(view)
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, i: Int) {
        val item = filteredVehicleItems[i]

        /*item.attendance?.let {
            if (it.meter_in != null && it.meter_out != null && it.startDate != SimpleDateFormat(
                    "dd-MM-yyyy",
                    Locale.ENGLISH
                ).format(
                    Date()
                )
            )
                item.attendance = null
        }*/

        holder.bind(item, i, clickListener)
    }

    override fun getItemCount(): Int = filteredVehicleItems.size
}