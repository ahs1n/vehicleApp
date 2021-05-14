package com.example.vehicleapp.base.repository

import com.example.vehicleapp.di.auth.AuthApi
import com.example.vehicleapp.di.local.VehicleDao
import com.example.vehicleapp.model.Users
import com.example.vehicleapp.model.Vehicles
import com.example.vehicleapp.model.VehiclesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class GeneralRepository @Inject constructor(
    private val apiService: AuthApi,
    private val vehicleDao: VehicleDao
) : GeneralDataSource {

    override suspend fun getAllUsers(
        table: String
    ): Flow<Users> {
        return flow {
//            emit(
//                apiService.getServerData(
//                    table = table
//                )
//            )
        }
    }

    override suspend fun getAllVehiclesFromRemote(table: String) = withContext(Dispatchers.IO) {
        vehicleDao.updateVehiclesData(apiService.getVehicleServerData())
    }

    override suspend fun getAllVehiclesFromDB(): Flow<List<VehiclesItem>> {
        return vehicleDao.readAllVehicles()
    }

    override suspend fun getSearchVehicleFromDB(vehicleNo: String): Flow<List<VehiclesItem>> {
        return vehicleDao.readSpecificVehicle(vehicleNo)
    }

}