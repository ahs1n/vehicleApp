package com.example.vehicleapp.base.repository

import com.example.vehicleapp.model.*
import kotlinx.coroutines.flow.Flow

/**
 * @author AliAzazAlam on 5/4/2021.
 */
interface GeneralDataSource {

    /*
    * Get all users from server
    * */
    suspend fun getAllUsers()
    /*
    * Get all users from server End
    * */


    /*
    * Get all vehicles
    * */
    suspend fun getAllVehiclesFromRemote()

    suspend fun getAllVehiclesFromDB(): Flow<List<VehiclesItem>>

    suspend fun getSearchVehicleFromDB(vehicleNo: String): Flow<List<VehiclesItem>>
    /*
    * Get all vehicles End
    * */


    /*
    * For login Start
    * */
    suspend fun getLoginInformation(username: String, password: String): UsersItem
    /*
    * For login End
    * */


}