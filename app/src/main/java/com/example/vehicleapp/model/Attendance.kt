package com.example.vehicleapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vehicleapp.utils.CONSTANTS
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = CONSTANTS.ATTENDANCE_TABLE)
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val vehicleNo: String,
    val driverName: String,
    val meter_in: String,
    val meter_out: String,
    val remarks: String,
    val todayDate: String

) : Parcelable