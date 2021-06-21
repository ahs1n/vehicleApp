package com.example.vehicleapp.di.local

import androidx.room.*
import com.example.vehicleapp.model.Users
import com.example.vehicleapp.model.UsersItem
import com.example.vehicleapp.model.Vehicles
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.utils.CONSTANTS
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    /*
    * Vehicle_driver
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllVehicles(vehicles: Vehicles)

    @Query("DELETE FROM ${CONSTANTS.VEHICLE_TABLE}")
    fun deleteAllVehicles()

    @Transaction
    fun updateVehiclesData(vehicles: Vehicles) {
        deleteAllVehicles()
        insertAllVehicles(vehicles)
    }

    @Query("SELECT * FROM  ${CONSTANTS.VEHICLE_TABLE} WHERE vehicleNo LIKE :vehicleNo ORDER BY id ASC")
    fun readSpecificVehicle(vehicleNo: String): Flow<List<VehiclesItem>>

    @Query("SELECT * FROM  ${CONSTANTS.VEHICLE_TABLE}")
    fun readAllVehicles(): Flow<List<VehiclesItem>>

    /*
    * Users
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllUsers(users: Users)

    @Query("DELETE FROM ${CONSTANTS.USER_TABLE}")
    fun deleteAllUsers()

    @Transaction
    fun updateUsersData(users: Users) {
        deleteAllUsers()
        insertAllUsers(users)
    }

    @Query("SELECT full_name,username,id FROM ${CONSTANTS.USER_TABLE} WHERE username = :username and password=:password")
    fun readUserExist(username: String, password: String): UsersItem?

    /*@Query("SELECT * FROM vehicle_driver")
    fun readAllVehicles(): Flow<List<VehicleAttendance>>*/

//    @Query("SELECT * FROM attendence WHERE vehilceNo = :vehicleNo")

}