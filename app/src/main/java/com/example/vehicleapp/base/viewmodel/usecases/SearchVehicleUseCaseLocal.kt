package com.example.vehicleapp.base.viewmodel.usecases

import com.example.vehicleapp.base.repository.GeneralDataSource
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class SearchVehicleUseCaseLocal @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke(
        vehicleNo: String
    ) = repository.getSearchVehicleFromDB(
        vehicleNo = vehicleNo
    )
}