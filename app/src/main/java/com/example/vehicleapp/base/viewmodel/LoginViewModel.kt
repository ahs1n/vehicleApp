package com.example.vehicleapp.base.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.base.repository.ResponseStates
import com.example.vehicleapp.base.repository.ResponseStatusCallbacks
import com.example.vehicleapp.base.repository.ResultCallBack
import com.example.vehicleapp.base.viewmodel.login_usecases.LoginUseCaseLocal
import com.example.vehicleapp.base.viewmodel.login_usecases.UserUseCase
import com.example.vehicleapp.model.UsersItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val loginUseCaseLocal: LoginUseCaseLocal,
    val userUseCase: UserUseCase
) : ViewModel() {

    private val TAG = LoginViewModel::class.java.simpleName

    private val _loginResponse: MutableLiveData<ResponseStatusCallbacks<UsersItem>> =
        MutableLiveData()

    val loginResponse: MutableLiveData<ResponseStatusCallbacks<UsersItem>>
        get() = _loginResponse

    private val _downloadUserData = MutableSharedFlow<ResponseStates<Any>>()
    val downloadUserData: SharedFlow<ResponseStates<Any>>
        get() = _downloadUserData

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

    /*
    * downloading vehicles data and register exception for exception handelling
    * */
    fun downloadingUsers() {
        viewModelScope.launch {
            _downloadUserData.emit(ResponseStates.Loading)
            userUseCase.invoke().let { data ->
                when (data) {
                    is ResultCallBack.CallException -> {
                        _downloadUserData.emit(ResponseStates.Error(message = data.exception.message.toString()))
                    }

                    is ResultCallBack.Error -> {
                        _downloadUserData.emit(ResponseStates.Error(message = data.error))
                    }

                    is ResultCallBack.Success -> {
                        _downloadUserData.emit(ResponseStates.Success(data = StringUtils.EMPTY))
                    }
                }
            }
        }
    }

}