package com.example.vehicleapp.base.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.base.repository.ResponseStatusCallbacks
import com.example.vehicleapp.base.repository.ResultCallBack
import com.example.vehicleapp.base.viewmodel.login_usecases.LoginUseCaseLocal
import com.example.vehicleapp.base.viewmodel.login_usecases.UserUseCase
import com.example.vehicleapp.model.UsersItem
import kotlinx.coroutines.launch
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

    val apiDownloadingDataProgress = MutableLiveData<Boolean>().apply { value = false }

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
        apiDownloadingDataProgress(true)
        viewModelScope.launch {
            userUseCase.invoke().let {data ->
                when(data){
                    is ResultCallBack.CallException -> {
                        apiDownloadingDataProgress(false)
                        Log.e(TAG, data.exception.message.toString() )
                    }
                    is ResultCallBack.Error -> {
                        apiDownloadingDataProgress(false)
                        Log.e(TAG, data.error )
                    }
                    is ResultCallBack.Success -> {
                        apiDownloadingDataProgress(false)
                    }
                }
            }
        }
    }

    private fun apiDownloadingDataProgress(flag: Boolean) {
        apiDownloadingDataProgress.value = flag
    }

}