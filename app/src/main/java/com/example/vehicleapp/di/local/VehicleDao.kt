package com.example.vehicleapp.di.local

import androidx.room.*
import com.example.vehicleapp.model.Users
import com.example.vehicleapp.model.Vehicles
import com.example.vehicleapp.model.VehiclesItem
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    /*
    * Vehicle_driver
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllVehicles(vehicles: Vehicles)

    @Query("DELETE FROM vehicle_driver")
    fun deleteAllVehicles()

    @Transaction
    fun updateVehiclesData(vehicles: Vehicles) {
        deleteAllVehicles()
        insertAllVehicles(vehicles)
    }

    /*
    * Users
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllUsers(users: Users)

    @Query("DELETE FROM users")
    fun deleteAllUsers()

    @Transaction
    fun updateUsersData(users: Users) {
        deleteAllUsers()
        insertAllUsers(users)
    }


    @Query("SELECT * FROM vehicle_driver WHERE vehicleNo LIKE :vehicleNo ORDER BY id ASC")
    fun readSpecificVehicle(vehicleNo: String): Flow<List<VehiclesItem>>

    @Query("SELECT * FROM vehicle_driver")
    fun readAllVehicles(): Flow<List<VehiclesItem>>

    /*@Query("SELECT * FROM vehicle_driver")
    fun readAllVehicles(): Flow<List<VehicleAttendance>>*/

//    @Query("SELECT * FROM attendence WHERE vehilceNo = :vehicleNo")

}