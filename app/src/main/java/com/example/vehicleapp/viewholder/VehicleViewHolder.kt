package com.example.vehicleapp.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicleapp.R
import androidx.databinding.library.baseAdapters.BR
import com.example.vehicleapp.databinding.ItemVehicleLayoutBinding
import com.example.vehicleapp.model.VehiclesItem

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class VehicleViewHolder(private val bi: ItemVehicleLayoutBinding) :
    RecyclerView.ViewHolder(bi.root) {

    fun bind(item: VehiclesItem) {
        bi.apply {
            bi.setVariable(BR.vehicleItem, item)
            bi.executePendingBindings()
        }
    }


    companion object {
        fun create(viewGroup: ViewGroup): VehicleViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_vehicle_layout, viewGroup, false)
            val binding = ItemVehicleLayoutBinding.bind(view)
            return VehicleViewHolder(binding)
        }
    }

    class ChildViewDiffUtils(
        private val oldList: ArrayList<VehiclesItem>,
        private val newList: ArrayList<VehiclesItem>
    ) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}