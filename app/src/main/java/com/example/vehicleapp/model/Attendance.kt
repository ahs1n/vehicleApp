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
    val uid: Int = 0,

    /*
    * Not accept null
    * */
    val vehicleNo: String,
    val deviceID: String,
    val user: String,

    /*
    * Accept null
    * */
    val driverName: String? = null,
    val meter_in: String? = null,
    val startDate: String? = null,
    val sysDate: String? = null,
    val meter_out: String? = null,
    val remarks: String? = null,
    val appversion: String? = null,
    val endDate: String? = null,
    val synced: Int = 0,
    val synced_date: String? = null
) : Parcelable