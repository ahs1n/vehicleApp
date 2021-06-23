package com.example.vehicleapp.base.viewmodel.login_usecases

import com.example.vehicleapp.base.repository.GeneralDataSource
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/17/2021.
 */
class LoginUseCaseLocal @Inject constructor(private val repository: GeneralDataSource) {
    suspend operator fun invoke(
        username: String,
        password: String,
    ) = repository.getLoginInformation(
        username = username,
        password = password
    )
}