package com.example.vehicleapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vehicleapp.utils.CONSTANTS
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = CONSTANTS.USER_TABLE)
data class UsersItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @Expose val full_name: String,
    @Expose val password: String?,
    @Expose val username: String
) : Parcelable