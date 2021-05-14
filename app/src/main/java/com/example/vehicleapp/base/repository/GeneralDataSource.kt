package com.example.vehicleapp.base.repository

import com.example.vehicleapp.model.Users
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.Vehicles
import com.example.vehicleapp.model.VehiclesItem
import kotlinx.coroutines.flow.Flow

/**
 * @author AliAzazAlam on 5/4/2021.
 */
interface GeneralDataSource {

    /*
    * Get all users from server
    * */
    suspend fun getAllUsers(
        table: String
    ): Flow<Users>
    /*
    * Get all users from server End
    * */

    /*
    * Get all vehicles
    * */
    suspend fun getAllVehiclesFromRemote(
        table: String
    )

    suspend fun getAllVehiclesFromDB(): Flow<List<VehiclesItem>>

    suspend fun getSearchVehicleFromDB(vehicleNo: String): Flow<List<VehiclesItem>>
    /*
    * Get all vehicles End
    * */


}