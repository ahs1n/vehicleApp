package com.example.vehicleapp.base.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.base.repository.ResponseStates
import com.example.vehicleapp.base.repository.ResponseStatusCallbacks
import com.example.vehicleapp.base.repository.ResultCallBack
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.GetAllAttendanceUseCaseLocal
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.SearchVehicleUseCaseLocal
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.UploadAttendanceUseCaseRemote
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.VehicleUseCaseLocal
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.VehicleUseCaseRemote
import com.example.vehicleapp.di.shared.PrefManager
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.utils.CONSTANTS
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val vehicleUseCaseRemote: VehicleUseCaseRemote,
    private val vehicleUseCaseLocal: VehicleUseCaseLocal,
    private val searchVehicleUseCaseLocal: SearchVehicleUseCaseLocal,
    private val getAllAttendanceUseCaseLocal: GetAllAttendanceUseCaseLocal,
    private val uploadAttendanceUseCaseRemote: UploadAttendanceUseCaseRemote,
    sharedPreferences: PrefManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val TAG = VehicleViewModel::class.java.simpleName
    private var searchVehicle = StringUtils.EMPTY

    val locationId =
        sharedPreferences.get(CONSTANTS.USER_LOCATION, StringUtils.EMPTY) ?: StringUtils.EMPTY

    private val _vehicleListDB =
        MutableStateFlow<ResponseStates<ArrayList<VehicleAttendance>>>(ResponseStates.Success(data = arrayListOf()))
    val vehicleListDB = _vehicleListDB.asStateFlow()


    private val _selectedVehicle: MutableLiveData<ResponseStatusCallbacks<VehiclesItem>> =
        MutableLiveData()
    val selectedVehicleResponse: MutableLiveData<ResponseStatusCallbacks<VehiclesItem>>
        get() = _selectedVehicle

    val apiDownloadingDataProgress = MutableLiveData<Boolean>().apply { value = false }

    val responseUpload = MutableLiveData<String>().apply { value = StringUtils.EMPTY }

    init {
        fetchVehiclesFromLocalDB(locationId)
    }

    /*
    * downloading vehicles data and register exception for exception handelling
    * */
    fun downloadingVehicles() {
        apiDownloadingDataProgress(true)
        viewModelScope.launch {
            vehicleUseCaseRemote(locationId).let { data ->
                when (data) {
                    is ResultCallBack.CallException -> {
                        apiDownloadingDataProgress(false)
                        Log.e(TAG, data.exception.message.toString())
                    }

                    is ResultCallBack.Error -> {
                        apiDownloadingDataProgress(false)
                        Log.e(TAG, data.error)
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

    /*
    * Observed function for initiate searching
    * */
    fun fetchVehiclesFromLocalDB(locationId: String) {
        viewModelScope.launch {
            vehicleUseCaseLocal.invoke(locationId).collectLatest {
                _vehicleListDB.emit(it)
            }
        }
    }

    /*
    * Retry connection if internet is not available
    * */
    fun retryConnection() {
        if (searchVehicle == StringUtils.EMPTY) {
            fetchVehiclesFromLocalDB(locationId)
        } else {
            fetchSearchVehiclesFromLocalDB(searchVehicle, locationId)
        }
    }

    /*
    * Search function for searching vehicles by vehicle_no
    * */
    fun searchVehicleFromDB(vehicleNo: String) {
        searchVehicle = vehicleNo
        if (searchVehicle == StringUtils.EMPTY) {
            fetchVehiclesFromLocalDB(locationId)
        } else
            fetchSearchVehiclesFromLocalDB(vehicleNo, locationId)
    }

    /*
    * Query to fetch vehicles from server
    * */
    private fun fetchSearchVehiclesFromLocalDB(search: String, locationId: String) {
        viewModelScope.launch {
            searchVehicleUseCaseLocal(
                vehicleNo = "%$search%",
                locationId
            ).collectLatest {
                _vehicleListDB.emit(it)
            }
        }
    }

    /*
    * Get all attendance from DB and upload to server
    * */
    fun uploadDataToServer() {
        apiDownloadingDataProgress(true)
        viewModelScope.launch {

            getAllAttendanceUseCaseLocal.invoke().let {
                if (it.isNotEmpty()) {
                    uploadAttendanceUseCaseRemote.invoke(
                        it
                    ).let { data ->
                        when (data) {
                            is ResultCallBack.CallException -> {
                                apiDownloadingDataProgress(false)
                                Log.e(TAG, data.exception.message.toString())
                                responseUpload.value = data.exception.message.toString()
                            }

                            is ResultCallBack.Error -> {
                                apiDownloadingDataProgress(false)
                                Log.e(TAG, data.error)
                                responseUpload.value = data.error
                            }

                            is ResultCallBack.Success -> {
                                apiDownloadingDataProgress(false)
                                responseUpload.value =
                                    "Uploaded records result: ${data.data.message}"
                            }
                        }
                    }
                } else {
                    apiDownloadingDataProgress(false)
                    responseUpload.value = "No new records to upload to the server"
                }
            }


        }
    }
}