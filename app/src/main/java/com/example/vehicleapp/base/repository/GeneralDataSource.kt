package com.example.vehicleapp.base.repository

import com.example.vehicleapp.model.*
import com.example.vehicleapp.model.utils.Users
import com.example.vehicleapp.model.utils.Vehicles
import kotlinx.coroutines.flow.Flow

/**
 * @author AliAzazAlam on 5/4/2021.
 */
interface GeneralDataSource {

    /*
    * Get all users from server
    * */
    suspend fun getAllUsers():ResultCallBack<Users>
    /*
    * Get all users from server End
    * */


    /*
    * Get all vehicles
    * */
    suspend fun getAllVehiclesFromRemote():ResultCallBack<Vehicles>

    suspend fun getAllVehiclesFromDB(): Flow<List<VehiclesItem>>

    suspend fun getAllVehiclesAndAttendanceFromDB(): Flow<List<VehicleAttendance>>

    suspend fun getSearchVehicleFromDB(vehicleNo: String): Flow<List<VehicleAttendance>>

    suspend fun insertAttendanceVehicle(attendance: Attendance): Long

    suspend fun updateAttendanceVehicle(attendance: Attendance): Int
    /*
    * Get all vehicles End
    * */


    /*
    * For login Start
    * */
    suspend fun getLoginInformation(username: String, password: String): UsersItem?
    /*
    * For login End
    * */


}