package com.example.vehicleapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.example.vehicleapp.MainApp
import com.example.vehicleapp.R
import com.example.vehicleapp.base.FragmentBase
import com.example.vehicleapp.base.repository.ResultCallBack
import com.example.vehicleapp.base.viewmodel.AttendanceViewModel
import com.example.vehicleapp.databinding.FragmentVehicleDetailBinding
import com.example.vehicleapp.di.shared.SharedStorage
import com.example.vehicleapp.model.Attendance
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.ui.MainActivity
import com.example.vehicleapp.ui.login_activity.LoginActivity
import com.example.vehicleapp.utils.CustomProgressDialog
import com.example.vehicleapp.utils.obtainViewModel
import com.example.vehicleapp.utils.showSnackBar
import com.example.vehicleapp.utils.toastUtil
import com.validatorcrawler.aliazaz.Validator
import java.text.SimpleDateFormat
import java.util.*


class VehicleDetailFragment : FragmentBase() {

    lateinit var viewModel: AttendanceViewModel
    lateinit var bi: FragmentVehicleDetailBinding

    private val vehicleRecord: VehiclesItem by lazy {
        arguments?.get("selectedVehicle") as VehiclesItem
    }

    private val attendanceRecord: Attendance? by lazy {
        arguments?.get("attendanceVehicle")?.let { it as Attendance }
    }
    private lateinit var form: Attendance

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

        //Manipulating attendance object
        (attendanceRecord == null).let {
            form = Attendance(
                vehicleNo = vehicleRecord.vehicleNo,

                /*deviceID = Settings.Secure.getString(
                    activity?.contentResolver,
                    Settings.Secure.ANDROID_ID
                ),*/
                deviceID = LoginActivity.deviceId,

                user = SharedStorage.getLogInUserName(sharedPrefImpl),
                _uid = MainApp.generateUid()
            )
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search_menu).isVisible = false
        menu.findItem(R.id.options_group).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
        * Initializing databinding
        * */
        bi = FragmentVehicleDetailBinding.inflate(inflater, container, false)
        bi.callback = this

        /*
        * Passing data to view
        * */
        bi.setVariable(BR.vehicleItem, attendanceRecord)

        //Set vehicle no as title
        (activity as MainActivity).supportActionBar?.title =
            "${vehicleRecord.model} ${vehicleRecord.vehicleNo.uppercase(Locale.ENGLISH)}"


        return bi.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
        * Obtaining ViewModel
        * */
        viewModel = obtainViewModel(
            this,
            AttendanceViewModel::class.java,
            viewModelFactory
        )

        /*
        * Alert start progress
        * */
        viewModel.apiDownloadingDataProgress.observe(viewLifecycleOwner, {
            if (it) {
                CustomProgressDialog.show(
                    activity as MainActivity,
                    getString(R.string.processing_attendance)
                )
            } else {
                CustomProgressDialog.dismiss()
            }
        })

        /*
        * Observe data inserted and updated
        * */
        viewModel.attendanceForm.observe(viewLifecycleOwner, {
            when (it) {
                is ResultCallBack.CallException -> {
                    bi.clAttendenceForm.showSnackBar(
                        message = it.exception.message.toString(),
                        action = "Got It!",
                        actionListener = {}
                    )
                }
                is ResultCallBack.Success -> {
                    "Attendance recorded successfully".toastUtil().show()
                    findNavController().popBackStack()
                }
                is ResultCallBack.Error -> TODO()
            }
        })

    }

    /*
    * Clickable buttons
    * */
    @SuppressLint("HardwareIds")
    fun timeInBtn(view: View) {
        if (!Validator.emptyCheckingContainer(requireContext(), bi.clAttendenceForm)) return

        if (!this::form.isInitialized) {
            "App cannot work please coordinate with IT team".toastUtil().show()
            return
        }

        val timeInAttendance = form.copy(
            driverName = bi.updateDriverName.text.toString(),
            meter_in = bi.updateMeterIn.text.toString(),
            startDate = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(Date()),
            sysDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH).format(Date().time),
            remarks = bi.updateRemarks.text.toString()
        )

        timeInAttendance.let {
            viewModel.insertAttendanceForm(it)
        }
    }

    fun timeOutBtn(view: View) {
        if (!Validator.emptyCheckingContainer(requireContext(), bi.clAttendenceForm)) return

        if (Integer.parseInt(bi.updateMeterOut.text.toString()) < Integer.parseInt(bi.updateMeterIn.text.toString())) {
//            "MeterOut Reading should be greater than or equal to MeterIn Reading".toastUtil().show()
            bi.updateMeterOut.error =
                "MeterOut Reading should be greater than or equal to MeterIn Reading"
            return
        }

        val timeInAttendance = attendanceRecord?.copy(
            meter_out = bi.updateMeterOut.text.toString(),
            remarks = bi.updateRemarks.text.toString(),
            endDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH).format(Date().time)
        )

        timeInAttendance?.let {
            viewModel.updateAttendanceForm(it)
        }
    }
}