package com.example.vehicleapp.base.viewmodel.vehicle_usecases

import android.content.Context
import com.example.vehicleapp.R
import com.example.vehicleapp.base.repository.GeneralDataSource
import com.example.vehicleapp.base.repository.ResponseStates
import com.example.vehicleapp.model.VehicleAttendance
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class SearchVehicleUseCaseLocal @Inject constructor(
    private val repository: GeneralDataSource,
    @ApplicationContext val context: Context
) {
    suspend operator fun invoke(
        vehicleNo: String,
        locationId: String
    ): Flow<ResponseStates<ArrayList<VehicleAttendance>>> = channelFlow {
        send(ResponseStates.Loading)
        repository.getSearchVehicleFromDB(
            vehicleNo = vehicleNo,
            locationId
        ).collectLatest { dataset ->
            if (dataset.isEmpty()) {
                send(
                    ResponseStates.Success(
                        data = arrayListOf<VehicleAttendance>(),
                        message = context.getString(R.string.empty_vehicle_message)
                    )
                )
            } else {
                val resultedVehiclesList = dataset.map {
                    it.copy(
                        attendance =
                        if (it.attendance?.meter_in != null && it.attendance?.meter_out != null && it.attendance?.startDate != SimpleDateFormat(
                                "dd-MM-yyyy",
                                Locale.ENGLISH
                            ).format(
                                Date()
                            )
                        )
                            null
                        else it.attendance
                    )
                }

                val sortedVehiclesList = resultedVehiclesList as ArrayList
                sortedVehiclesList.sortByDescending { it.attendance?.let { item -> item.meter_in != null && item.meter_out == null } }
                send(ResponseStates.Success(sortedVehiclesList))
            }
        }
    }.flowOn(Dispatchers.IO)
}