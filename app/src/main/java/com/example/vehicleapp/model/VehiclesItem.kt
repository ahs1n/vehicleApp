package com.example.vehicleapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "vehicle_driver")
data class VehiclesItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @Expose val driverCNIC: String,
    @Expose val driverName: String,
    @Expose val timeFlag: Int,
    @Expose val vehicleNo: String
)