package com.example.vehicleapp.base.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.base.repository.ResponseStatusCallbacks
import com.example.vehicleapp.base.repository.ResultCallBack
import com.example.vehicleapp.base.viewmodel.vehicle_usecases.*
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.VehiclesItem
import kotlinx.coroutines.flow.collect
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
    private val uploadAttendanceUseCaseRemote: UploadAttendanceUseCaseRemote
) : ViewModel() {

    private val TAG = VehicleViewModel::class.java.simpleName

    private val _vehicleList: MutableLiveData<ResponseStatusCallbacks<List<VehicleAttendance>>> =
        MutableLiveData()

    val vehiclesResponse: MutableLiveData<ResponseStatusCallbacks<List<VehicleAttendance>>>
        get() = _vehicleList

    private val _selectedVehicle: MutableLiveData<ResponseStatusCallbacks<VehiclesItem>> =
        MutableLiveData()

    val selectedVehicleResponse: MutableLiveData<ResponseStatusCallbacks<VehiclesItem>>
        get() = _selectedVehicle

    val apiDownloadingDataProgress = MutableLiveData<Boolean>().apply { value = false }

    val responseUpload = MutableLiveData<String>().apply { value = StringUtils.EMPTY }

    private var searchVehicle = StringUtils.EMPTY

    init {
        fetchVehiclesFromLocalDB()
    }

    /*
    * downloading vehicles data and register exception for exception handelling
    * */
    fun downloadingVehicles() {
        apiDownloadingDataProgress(true)
        viewModelScope.launch {
            vehicleUseCaseRemote.invoke().let { data ->
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
    fun fetchVehiclesFromLocalDB() {
        _vehicleList.value = ResponseStatusCallbacks.loading(data = null)
        viewModelScope.launch {
            try {
                vehicleUseCaseLocal().collect { dataset ->
                    if (dataset.isNullOrEmpty())
                        _vehicleList.value = ResponseStatusCallbacks.error(
                            data = null,
                            "Sorry vehicles not found"
                        )
                    else {
                        _vehicleList.value = ResponseStatusCallbacks.success(
                            data = dataset,
                            "Vehicles received"
                        )
                    }
                }
            } catch (e: Exception) {
                _vehicleList.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }

    /*
    * Send data to detail page
    * */
    fun setSelectedVehicle(singleImages: VehiclesItem) {
        _selectedVehicle.value = ResponseStatusCallbacks.loading(null)
        viewModelScope.launch {
            try {
                _selectedVehicle.value = ResponseStatusCallbacks.success(
                    data = singleImages,
                    "Vehicle received"
                )
            } catch (e: Exception) {
                _vehicleList.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }

    /*
    * Retry connection if internet is not available
    * */
    fun retryConnection() {
        if (searchVehicle == StringUtils.EMPTY) {
            fetchVehiclesFromLocalDB()
        } else {
            fetchSearchVehiclesFromLocalDB(searchVehicle)
        }
    }

    /*
    * Search function for searching vehicles by vehicle_no
    * */
    fun searchVehicleFromDB(vehicleNo: String) {
        searchVehicle = vehicleNo
        if (searchVehicle == StringUtils.EMPTY) {
            fetchVehiclesFromLocalDB()
        } else
            fetchSearchVehiclesFromLocalDB(vehicleNo)
    }

    /*
    * Query to fetch vehicles from server
    * */
    private fun fetchSearchVehiclesFromLocalDB(search: String) {
        _vehicleList.value = ResponseStatusCallbacks.loading(data = null)
        viewModelScope.launch {
            try {
                searchVehicleUseCaseLocal(vehicleNo = "%$search%").collect { dataset ->
                    if (dataset.isNullOrEmpty())
                        _vehicleList.value = ResponseStatusCallbacks.error(
                            data = null,
                            "Sorry no vehicle found"
                        )
                    else
                        _vehicleList.value = ResponseStatusCallbacks.success(
                            data = dataset,
                            "Vehicles received"
                        )
                }

            } catch (e: Exception) {
                _vehicleList.value = ResponseStatusCallbacks.error(null, e.message.toString())
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