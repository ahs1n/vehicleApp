package com.example.vehicleapp.di.local

import androidx.room.*
import com.example.vehicleapp.model.Attendance
import com.example.vehicleapp.model.utils.Users
import com.example.vehicleapp.model.UsersItem
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.utils.Vehicles
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
    fun readSpecificVehicleAndAttendance(vehicleNo: String): Flow<List<VehicleAttendance>>

    @Query("SELECT * FROM  ${CONSTANTS.VEHICLE_TABLE}")
    fun readAllVehicles(): Flow<List<VehiclesItem>>

    @Transaction
    @Query("SELECT * FROM ${CONSTANTS.VEHICLE_TABLE}")
    fun getVehicleAndAttendance(): Flow<List<VehicleAttendance>>

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

    /*
    * Attendance form
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAttendanceForm(attendance: Attendance): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateAttendanceForm(attendance: Attendance): Int

    @Query("SELECT * FROM ${CONSTANTS.ATTENDANCE_TABLE} WHERE synced = 0 AND meter_out != ''")
    fun getAllNoneSyncedAttendanceForm(): List<Attendance>

    @Query("UPDATE ${CONSTANTS.ATTENDANCE_TABLE} SET synced = 1 WHERE uid IN (:uids)")
    fun updateSyncedStatus(uids: IntArray): Int

}