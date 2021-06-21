package com.example.vehicleapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.viewholder.VehicleViewHolder
import kotlinx.android.synthetic.main.item_vehicle_layout.view.*

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class VehicleListAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<VehicleViewHolder>() {

    var vehicleItems: ArrayList<VehiclesItem> = ArrayList()
        set(value) {
            field = value
            val diffCallback =
                VehicleViewHolder.ChildViewDiffUtils(filteredVehicleItems, vehicleItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            if (filteredVehicleItems.size > 0)
                filteredVehicleItems.clear()
            filteredVehicleItems.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    private var filteredVehicleItems: ArrayList<VehiclesItem> = ArrayList()
        set(value) {
            field = value
            val diffCallback =
                VehicleViewHolder.ChildViewDiffUtils(filteredVehicleItems, vehicleItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    fun clearProductItem() {
        filteredVehicleItems.clear()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VehicleViewHolder {
        return VehicleViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, i: Int) {
        val item = filteredVehicleItems[i]
        holder.bind(item)
        holder.itemView.timeBtn.setOnClickListener {
            clickListener.onItemClick(item, i)
        }
    }

    override fun getItemCount(): Int = filteredVehicleItems.size

    interface OnItemClickListener {
        fun onItemClick(item: VehiclesItem, position: Int)
    }
}