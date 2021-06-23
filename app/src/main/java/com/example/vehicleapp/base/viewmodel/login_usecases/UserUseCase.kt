package com.example.vehicleapp.base.viewmodel.login_usecases

import com.example.vehicleapp.base.repository.GeneralDataSource
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class UserUseCase @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke() = repository.getAllUsers()
}