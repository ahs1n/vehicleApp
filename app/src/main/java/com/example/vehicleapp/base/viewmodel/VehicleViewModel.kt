package com.example.vehicleapp.base.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.R
import com.example.vehicleapp.base.repository.ResponseStates
import com.example.vehicleapp.base.repository.ResponseStatusCallbacks
import com.example.vehicleapp.base.repository.ResultCallBack
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.GetAllAttendanceUseCaseLocal
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.SearchVehicleUseCaseLocal
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.UploadAttendanceUseCaseRemote
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.VehicleUseCaseLocal
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.VehicleUseCaseRemote
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.utils.CONSTANTS
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

/**
 * @author AliAzazAlam on 5/4/2021.
 */
class VehicleViewModel @Inject constructor(
    private val vehicleUseCaseRemote: VehicleUseCaseRemote,
    private val vehicleUseCaseLocal: VehicleUseCaseLocal,
    private val searchVehicleUseCaseLocal: SearchVehicleUseCaseLocal,
    private val getAllAttendanceUseCaseLocal: GetAllAttendanceUseCaseLocal,
    private val uploadAttendanceUseCaseRemote: UploadAttendanceUseCaseRemote,
    sharedPreferences: SharedPreferences,
    private val context: Context
) : ViewModel() {

    private val TAG = VehicleViewModel::class.java.simpleName
    private var searchVehicle = StringUtils.EMPTY

    val locationId =
        sharedPreferences.getString(CONSTANTS.USER_LOCATION, StringUtils.EMPTY) ?: StringUtils.EMPTY

    private val _vehicleListDB = MutableSharedFlow<ResponseStates<ArrayList<VehicleAttendance>?>>()
    val vehicleListDB: SharedFlow<ResponseStates<ArrayList<VehicleAttendance>?>>
        get() = _vehicleListDB

    private val _selectedVehicle: MutableLiveData<ResponseStatusCallbacks<VehiclesItem>> =
        MutableLiveData()
    val selectedVehicleResponse: MutableLiveData<ResponseStatusCallbacks<VehiclesItem>>
        get() = _selectedVehicle

    val apiDownloadingDataProgress = MutableLiveData<Boolean>().apply { value = false }

    val responseUpload = MutableLiveData<String>().apply { value = StringUtils.EMPTY }

    /*
    * downloading vehicles data and register exception for exception handelling
    * */
    fun downloadingVehicles() {
        apiDownloadingDataProgress(true)
        viewModelScope.launch {
            vehicleUseCaseRemote.invoke(locationId).let { data ->
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
            _vehicleListDB.emit(ResponseStates.Loading)
            try {
                vehicleUseCaseLocal(locationId).collectLatest { dataset ->
                    if (dataset.isEmpty())
                        _vehicleListDB.emit(
                            ResponseStates.Success(
                                data = null,
                                message = context.getString(R.string.empty_vehicle_message)
                            )
                        )
                    else {
                        val sortedVehiclesList = dataset as ArrayList
                        sortedVehiclesList.sortByDescending { it.attendance?.let { item -> item.meter_in != null && item.meter_out == null } }
                        _vehicleListDB.emit(ResponseStates.Success(sortedVehiclesList))
                    }
                }
            } catch (e: Exception) {
                _vehicleListDB.emit(ResponseStates.Error(e.message.toString()))
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
            _vehicleListDB.emit(ResponseStates.Loading)
            try {
                searchVehicleUseCaseLocal(
                    vehicleNo = "%$search%",
                    locationId
                ).collectLatest { dataset ->
                    if (dataset.isEmpty())
                        _vehicleListDB.emit(
                            ResponseStates.Success(
                                data = null,
                                message = context.getString(R.string.empty_vehicle_message)
                            )
                        )
                    else {
                        val sortedVehiclesList = dataset as ArrayList
                        sortedVehiclesList.sortByDescending { it.attendance?.let { item -> item.meter_in != null && item.meter_out == null } }
                        _vehicleListDB.emit(ResponseStates.Success(sortedVehiclesList))
                    }
                }
            } catch (e: Exception) {
                _vehicleListDB.emit(ResponseStates.Error(e.message.toString()))
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