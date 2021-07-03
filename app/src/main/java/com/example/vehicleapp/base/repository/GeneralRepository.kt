package com.example.vehicleapp.base.repository

import androidx.annotation.WorkerThread
import com.example.vehicleapp.di.auth.AuthApi
import com.example.vehicleapp.di.auth.remote.message
import com.example.vehicleapp.di.auth.remote.onErrorSuspend
import com.example.vehicleapp.di.auth.remote.onExceptionSuspend
import com.example.vehicleapp.di.auth.remote.onSuccessSuspend
import com.example.vehicleapp.di.local.VehicleDao
import com.example.vehicleapp.model.Attendance
import com.example.vehicleapp.model.UsersItem
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.model.response.ServerUploadReturn
import com.example.vehicleapp.model.utils.Users
import com.example.vehicleapp.model.utils.Vehicles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class GeneralRepository @Inject constructor(
    private val apiService: AuthApi,
    private val vehicleDao: VehicleDao
) : GeneralDataSource {

    @WorkerThread
    override suspend fun getAllUsers(): ResultCallBack<Users> {
        var result: ResultCallBack<Users>? = null
        apiService.getUserServerData().apply {
            this.onSuccessSuspend {
                result = data?.let {
                    withContext(Dispatchers.IO) {
                        vehicleDao.updateUsersData(
                            it
                        )
                        ResultCallBack.Success(it)
                    }
                } ?: ResultCallBack.Error("Empty records received from server")
            }.onErrorSuspend {
                result = ResultCallBack.Error(message())
            }.onExceptionSuspend {
                result = ResultCallBack.CallException(exception = this.exception as Exception)
            }
        }
        return result!!
    }

    @WorkerThread
    override suspend fun getAllVehiclesFromRemote(): ResultCallBack<Vehicles> {
        var result: ResultCallBack<Vehicles>? = null
        apiService.getVehicleServerData().apply {
            this.onSuccessSuspend {
                result = data?.let {
                    withContext(Dispatchers.IO) {
                        vehicleDao.updateVehiclesData(
                            it
                        )
                        ResultCallBack.Success(it)
                    }
                } ?: ResultCallBack.Error("Empty records received from server")
            }.onErrorSuspend {
                result = ResultCallBack.Error(message())
            }.onExceptionSuspend {
                result = ResultCallBack.CallException(exception = this.exception as Exception)
            }
        }
        return result!!
    }

    override suspend fun getAllVehiclesFromDB(): Flow<List<VehiclesItem>> {
        return vehicleDao.readAllVehicles()
    }

    override suspend fun getAllVehiclesAndAttendanceFromDB(): Flow<List<VehicleAttendance>> {
        return vehicleDao.getVehicleAndAttendance()
    }

    override suspend fun getSearchVehicleFromDB(vehicleNo: String): Flow<List<VehicleAttendance>> {
        return vehicleDao.readSpecificVehicleAndAttendance(vehicleNo)
    }

    override suspend fun getLoginInformation(username: String, password: String): UsersItem? =
        withContext(Dispatchers.IO) {
            vehicleDao.readUserExist(username, password)
        }

    override suspend fun insertAttendanceVehicle(attendance: Attendance): Long =
        withContext(Dispatchers.IO) {
            vehicleDao.insertAttendanceForm(attendance)
        }

    override suspend fun updateAttendanceVehicle(attendance: Attendance): Int =
        withContext(Dispatchers.IO) {
            vehicleDao.updateAttendanceForm(attendance)
        }

    override suspend fun getAllAttendanceFormFromLocalDB(): List<Attendance> =
        withContext(Dispatchers.IO) {
            vehicleDao.getAllNoneSyncedAttendanceForm()
        }

    override suspend fun uploadDataToRemoteServer(attendanceLst: List<Attendance>): ResultCallBack<ServerUploadReturn> {
        var result: ResultCallBack<ServerUploadReturn>? = null
        apiService.uploadAttendanceDataToServer(attendance = attendanceLst).apply {
            this.onSuccessSuspend {
                result = data?.let {
                    withContext(Dispatchers.IO){
                        vehicleDao.updateSyncedStatus(
                            IntArray(it.uids.size) {item-> it.uids[item] }
                        )
                        ResultCallBack.Success(it)
                    }
                }
            }.onErrorSuspend {
                result = ResultCallBack.Error(message())
            }.onExceptionSuspend {
                result = ResultCallBack.CallException(exception = this.exception as Exception)
            }
        }
        return result!!
    }
}