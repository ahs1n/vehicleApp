package com.example.vehicleapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vehicleapp.utils.CONSTANTS
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = CONSTANTS.VEHICLE_TABLE)
data class VehiclesItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @Expose val vehicleNo: String,
    @Expose val model: String,
    @Expose val location: String,
    @Expose val location_id: String,

    //Check whether timeIn information is available or not
    @Expose val dataExist: Boolean = false
) : Parcelable