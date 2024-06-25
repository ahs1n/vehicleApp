package com.example.vehicleapp.utils

import com.example.vehicleapp.model.Attendance
import com.example.vehicleapp.model.VehiclesItem

fun interface GenericListeners {
    fun onItemClick(item: VehiclesItem, attendance: Attendance?, position: Int)
}