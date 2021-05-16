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
    ) = withContext(Dispatchers.IO) {
        vehicleDao.updateUsersData(
            apiService.getUserServerData()
        )
    }

    override suspend fun getAllVehiclesFromRemote(
    ) = withContext(Dispatchers.IO) {
        val v = Vehicles()
        v.addAll(
            apiService.getVehicleServerData()
        )
        vehicleDao.updateVehiclesData(
            v
        )

    }

    override suspend fun getAllVehiclesFromDB(): Flow<List<VehiclesItem>> {
        return vehicleDao.readAllVehicles()
    }

    override suspend fun getSearchVehicleFromDB(vehicleNo: String): Flow<List<VehiclesItem>> {
        return vehicleDao.readSpecificVehicle(vehicleNo)
    }

}