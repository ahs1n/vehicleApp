package com.example.vehicleapp.di.auth

import com.example.vehicleapp.model.utils.Users
import com.example.vehicleapp.model.utils.Vehicles
import com.example.vehicleapp.utils.CONSTANTS
import retrofit2.http.GET

/**
 * @author AliAzazAlam on 5/4/2021.
 */
interface AuthApi {

    @GET(ApiRoutes.GET_DATA + CONSTANTS.VEHICLE_TABLE)
    suspend fun getVehicleServerData(): Vehicles

    @GET(ApiRoutes.GET_DATA + CONSTANTS.USER_TABLE)
    suspend fun getUserServerData(): Users


}