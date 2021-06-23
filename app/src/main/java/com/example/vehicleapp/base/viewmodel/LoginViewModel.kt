package com.example.vehicleapp.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.base.repository.ResponseStatusCallbacks
import com.example.vehicleapp.base.viewmodel.login_usecases.LoginUseCaseLocal
import com.example.vehicleapp.model.UsersItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(val loginUseCaseLocal: LoginUseCaseLocal) : ViewModel() {

    private val _loginResponse: MutableLiveData<ResponseStatusCallbacks<UsersItem>> =
        MutableLiveData()

    val loginResponse: MutableLiveData<ResponseStatusCallbacks<UsersItem>>
        get() = _loginResponse

    fun getLoginInfoFromDB(username: String, password: String) {
        _loginResponse.value = ResponseStatusCallbacks.loading(null)
        viewModelScope.launch {
            try {
                val loginData = loginUseCaseLocal(username, password)
                _loginResponse.value = loginData?.let {
                    ResponseStatusCallbacks.success(data = it, "User exist")
                } ?: ResponseStatusCallbacks.error(null, "User not exist")
            } catch (e: Exception) {
                _loginResponse.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }

}