package com.example.vehicleapp.base.viewmodel.attendance_usecases

import com.example.vehicleapp.base.repository.GeneralRepository
import com.example.vehicleapp.model.Attendance
import javax.inject.Inject

/**
 * @author AliAzazAlam on 6/23/2021.
 */
class InsertAttendanceFormUseCase @Inject constructor(private val repository: GeneralRepository) {

    suspend operator fun invoke(
        attendance: Attendance
    ) = repository.insertAttendanceVehicle(attendance = attendance)

}