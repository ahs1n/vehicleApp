package com.example.vehicleapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "vehicles")
data class VehiclesItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @Expose val col_dt: String,
    @Expose val col_id: Int,
    @Expose val contractDuration: String,
    @Expose val contractEndDate: String,
    @Expose val contractID: String,
    @Expose val contractStartDate: String,
    @Expose val location: String,
    @Expose val make: String,
    @Expose val model: String,
    @Expose val ownerAddress: String,
    @Expose val ownerCNIC: String,
    @Expose val ownerContact: String,
    @Expose val ownerName: String,
    @Expose val registrationYear: String,
    @Expose val rentCNGperKM: String,
    @Expose val rentFixed: String,
    @Expose val rentPetrolperKM: String,
    @Expose val subLocation: String,
    @Expose val taxStatusMonth: String,
    @Expose val taxStatusYear: String,
    @Expose val vehicleNo: String,

    //Check whether timeIn information is available or not
    @Expose val dataExist: Boolean = false
)