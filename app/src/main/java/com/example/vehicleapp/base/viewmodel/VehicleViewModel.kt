package com.example.vehicleapp.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.base.repository.ResponseStatusCallbacks
import com.example.vehicleapp.base.viewmodel.usecases.SearchVehicleUseCaseLocal
import com.example.vehicleapp.base.viewmodel.usecases.VehicleUseCaseRemote
import com.example.vehicleapp.base.viewmodel.usecases.VehicleUseCaseLocal
import com.example.vehicleapp.model.Vehicles
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.utils.CONSTANTS
import kotlinx.coroutines.flow.Flow
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
    private val searchVehicleUseCaseLocal: SearchVehicleUseCaseLocal
) : ViewModel() {

    private val _vehicleList: MutableLiveData<ResponseStatusCallbacks<List<VehiclesItem>>> =
        MutableLiveData()

    val vehiclesResponse: MutableLiveData<ResponseStatusCallbacks<List<VehiclesItem>>>
        get() = _vehicleList

    private val _selectedVehicle: MutableLiveData<ResponseStatusCallbacks<VehiclesItem>> =
        MutableLiveData()

    val selectedVehicleResponse: MutableLiveData<ResponseStatusCallbacks<VehiclesItem>>
        get() = _selectedVehicle

    private var searchVehicle = StringUtils.EMPTY

    init {
        fetchVehiclesFromLocalDB()
    }

    /*
    * downloading vehicles data
    * */
    fun downloadingVehicles() {
        viewModelScope.launch {
            vehicleUseCaseRemote.invoke(CONSTANTS.VEHICLE_TABLE)
        }
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
    fun searchImagesFromRemote(search: String) {
        searchVehicle = search
        fetchSearchVehiclesFromLocalDB(search)
    }

    /*
    * Query to fetch vehicles from server
    * */
    private fun fetchSearchVehiclesFromLocalDB(search: String) {
        _vehicleList.value = ResponseStatusCallbacks.loading(data = null)
        viewModelScope.launch {
            try {
                searchVehicleUseCaseLocal(vehicleNo = search).collect { dataset ->
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

}