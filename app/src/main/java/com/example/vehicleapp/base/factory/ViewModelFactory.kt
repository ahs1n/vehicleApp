package com.example.vehicleapp.base.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vehicleapp.base.repository.GeneralRepository
import com.example.vehicleapp.base.viewmodel.VehicleViewModel
import com.example.vehicleapp.base.viewmodel.usecases.SearchVehicleUseCaseLocal
import com.example.vehicleapp.base.viewmodel.usecases.VehicleUseCaseRemote
import com.example.vehicleapp.base.viewmodel.usecases.UserUseCase
import com.example.vehicleapp.base.viewmodel.usecases.VehicleUseCaseLocal
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
            modelClass.isAssignableFrom(VehicleViewModel::class.java) -> VehicleViewModel(
                VehicleUseCaseRemote(repository),
                VehicleUseCaseLocal(repository),
                SearchVehicleUseCaseLocal(repository),
            ) as T
            else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
        }
    }

}