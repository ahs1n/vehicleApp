package com.example.vehicleapp.base.viewmodel.vehicle_usecases

import com.example.vehicleapp.base.repository.GeneralDataSource
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class VehicleUseCaseLocal @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke(
        location_id: String
//    ) = repository.getAllVehiclesFromDB()
    ) = repository.getAllVehiclesAndAttendanceFromDB(location_id)
}