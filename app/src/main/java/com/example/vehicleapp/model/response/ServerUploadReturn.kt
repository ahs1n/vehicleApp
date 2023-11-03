package com.example.vehicleapp.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author AliAzazAlam on 7/1/2021.
 */
data class ServerUploadReturn(
    @SerializedName("error")
    @Expose val error: String,

    @SerializedName("message")
    @Expose val message: String,

    @SerializedName("uids")
    @Expose val uids: List<String>,

    )