package com.example.vehicleapp.base.repository

import com.example.vehicleapp.model.Attendance
import com.example.vehicleapp.model.UsersItem
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.model.response.ServerUploadReturn
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
    suspend fun getAllUsers(): ResultCallBack<Users>
    /*
    * Get all users from server End
    * */


    /*
    * Get all vehicles
    * */
    suspend fun getAllVehiclesFromRemote(location_id: String): ResultCallBack<Vehicles>

    suspend fun getAllVehiclesFromDB(): Flow<List<VehiclesItem>>

    suspend fun getAllVehiclesAndAttendanceFromDB(location_id: String): Flow<List<VehicleAttendance>>

    suspend fun getSearchVehicleFromDB(vehicleNo: String, location_id: String): Flow<List<VehicleAttendance>>

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

    /*
    * Get Attendance form Start
    * */
    suspend fun getAllAttendanceFormFromLocalDB(): List<Attendance>

    suspend fun uploadDataToRemoteServer(attendanceLst: List<Attendance>): ResultCallBack<ServerUploadReturn>

    /*
    * Get Attendance form End
    * */


}