package com.example.vehicleapp.base.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vehicleapp.base.repository.GeneralRepository
import com.example.vehicleapp.base.viewmodel.AttendanceViewModel
import com.example.vehicleapp.base.viewmodel.LoginViewModel
import com.example.vehicleapp.base.viewmodel.VehicleViewModel
import com.example.vehicleapp.base.viewmodel.attendance_usecases.InsertAttendanceFormUseCase
import com.example.vehicleapp.base.viewmodel.attendance_usecases.UpdateAttendanceFormUseCase
import com.example.vehicleapp.base.viewmodel.login_usecases.LoginUseCaseLocal
import com.example.vehicleapp.base.viewmodel.login_usecases.UserUseCase
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author AliAzazAlam on 5/4/2021.
 */
@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val repository: GeneralRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                LoginUseCaseLocal(repository),
                UserUseCase(repository)
            ) as T
            modelClass.isAssignableFrom(VehicleViewModel::class.java) -> VehicleViewModel(
                VehicleUseCaseRemote(repository),
                VehicleUseCaseLocal(repository),
                SearchVehicleUseCaseLocal(repository),
            ) as T
            modelClass.isAssignableFrom(AttendanceViewModel::class.java) -> AttendanceViewModel(
                InsertAttendanceFormUseCase(repository),
                UpdateAttendanceFormUseCase(repository)
            ) as T
            else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
        }
    }

}