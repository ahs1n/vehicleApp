package com.example.vehicleapp.di.auth

import com.example.vehicleapp.model.Vehicles
import com.example.vehicleapp.utils.CONSTANTS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author AliAzazAlam on 5/4/2021.
 */
interface AuthApi {

    @GET(ApiRoutes.GET_DATA + CONSTANTS.VEHICLE_TABLE)
    suspend fun getVehicleServerData(): Vehicles


}