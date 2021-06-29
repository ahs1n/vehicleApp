package com.example.vehicleapp.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicleapp.base.repository.ResultCallBack
import com.example.vehicleapp.base.viewmodel.attendance_usecases.InsertAttendanceFormUseCase
import com.example.vehicleapp.base.viewmodel.attendance_usecases.UpdateAttendanceFormUseCase
import com.example.vehicleapp.model.Attendance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author AliAzazAlam on 6/23/2021.
 */
class AttendanceViewModel @Inject constructor(
    private val insertAttendanceFormUseCase: InsertAttendanceFormUseCase,
    private val updateAttendanceFormUseCase: UpdateAttendanceFormUseCase
) : ViewModel() {

    private val _attendanceForm: MutableLiveData<ResultCallBack<Boolean>> = MutableLiveData()

    val attendanceForm: MutableLiveData<ResultCallBack<Boolean>>
        get() = _attendanceForm

    val apiDownloadingDataProgress = MutableLiveData<Boolean>().apply { value = false }

    private fun apiDownloadingDataProgress(flag: Boolean) {
        apiDownloadingDataProgress.value = flag
    }

    fun insertAttendanceForm(attendance: Attendance) {
        apiDownloadingDataProgress(true)
        viewModelScope.launch {
            delay(2000)
            val count = insertAttendanceFormUseCase.invoke(attendance).toInt()
            if (count > 0) _attendanceForm.value = ResultCallBack.Success(true)
            else _attendanceForm.value =
                ResultCallBack.CallException(Exception("Failed to insert record in database!"))
            apiDownloadingDataProgress(false)
        }
    }

    fun updateAttendanceForm(attendance: Attendance) {
        apiDownloadingDataProgress.value = true
        viewModelScope.launch {
            delay(2000)
            val count = updateAttendanceFormUseCase.invoke(attendance)
            if (count == 1) _attendanceForm.value = ResultCallBack.Success(true)
            else _attendanceForm.value =
                ResultCallBack.CallException(Exception("Failed to insert record in database!"))
        }
    }

}