package com.example.vehicleapp.base.viewmodel.vehicle_usecases

import com.example.vehicleapp.base.repository.GeneralDataSource
import com.example.vehicleapp.model.Attendance
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class UploadAttendanceUseCaseRemote @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke(
        attendance: List<Attendance>
    ) = repository.uploadDataToRemoteServer(
        attendance
    )
}