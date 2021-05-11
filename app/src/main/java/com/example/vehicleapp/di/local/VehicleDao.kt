package com.example.vehicleapp.di.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.Vehicles
import com.example.vehicleapp.model.VehiclesItem
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVehicle(vehicle: Vehicle)*/

    /*@Update
    suspend fun updateVehicle(vehicle: Vehicle)*/

    @Query("SELECT * FROM vehicle_driver WHERE vehicleNo LIKE :vehicleNo ORDER BY id ASC")
    fun readSpecificVehicle(vehicleNo: String): Flow<List<VehiclesItem>>

    @Query("SELECT * FROM vehicle_driver")
    fun readAllVehicles(): Flow<List<VehiclesItem>>

    /*@Query("SELECT * FROM vehicle_driver")
    fun readAllVehicles(): Flow<List<VehicleAttendance>>*/

//    @Query("SELECT * FROM attendence WHERE vehilceNo = :vehicleNo")

}