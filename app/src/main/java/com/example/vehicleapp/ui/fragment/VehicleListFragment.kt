package com.example.vehicleapp.ui.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicleapp.R
import com.example.vehicleapp.adapters.VehicleListAdapter
import com.example.vehicleapp.base.FragmentBase
import com.example.vehicleapp.base.repository.ResponseStatus
import com.example.vehicleapp.base.viewmodel.VehicleViewModel
import com.example.vehicleapp.databinding.FragmentVehicleListBinding
import com.example.vehicleapp.di.shared.SharedStorage
import com.example.vehicleapp.model.Attendance
import com.example.vehicleapp.model.VehicleAttendance
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.ui.MainActivity
import com.example.vehicleapp.ui.login_activity.LoginActivity
import com.example.vehicleapp.utils.*
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class VehicleListFragment : FragmentBase() {

    lateinit var viewModel: VehicleViewModel
    lateinit var adapter: VehicleListAdapter
    lateinit var bi: FragmentVehicleListBinding
    var actionBarHeight = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
        * Initializing databinding
        * */
        bi = FragmentVehicleListBinding.inflate(inflater, container, false)
        bi.setVariable(BR.callback, this)

        /*
        * Get actionbar height for use in translation
        * */
        context?.let { item ->
            actionBarHeight = with(TypedValue().also {
                item.theme.resolveAttribute(
                    android.R.attr.actionBarSize,
                    it,
                    true
                )
            }) {
                TypedValue.complexToDimensionPixelSize(this.data, resources.displayMetrics)
            }
        }

        /*
        * Translate items on menu click
        * */
        actionBarHeight *= -1
        bi.fldGrpSearchPhotos.translationY = actionBarHeight.toFloat()
        bi.nestedScrollView.translationY = actionBarHeight.toFloat() / 2

        return bi.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*
        * Obtaining ViewModel
        * */
        viewModel = obtainViewModel(
            this,
            VehicleViewModel::class.java,
            viewModelFactory
        )

        /*
        * Initiating recyclerview
        * */
        callingRecyclerView()

        /*
        * Fetch vehicles list
        * */
        viewModel.vehiclesResponse.observe(viewLifecycleOwner, { it ->
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    it.data?.run {
                        val recVehicle = it.data as ArrayList<VehicleAttendance>
                        if (recVehicle.isNotEmpty())
                            recVehicle.sortByDescending { it.attendance?.let { item -> item.meter_in != null && item.meter_out == null } }
                        adapter.vehicleItems = recVehicle
                        bi.multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    }
                }
                ResponseStatus.ERROR -> {
                    /*it.data?.let { item ->
                        if (item.page == 1)
                            bi.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                        else
                            bi.nestedScrollView.showSnackBar(
                                message = it.message.toString()
                            )
                    } ?: run {
                        bi.multiStateView.viewState = MultiStateView.ViewState.ERROR
                        bi.nestedScrollView.showSnackBar(
                            message = "Internet not available",
                            action = "Retry"
                        ) {
                            viewModel.retryConnection()
                        }

                    }*/

                    bi.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                }
                ResponseStatus.LOADING -> {
                    lifecycleScope.launch {
                        MultiStateView.ViewState.LOADING
                        delay(2000)
                    }
                }
            }

            viewModel.apiDownloadingDataProgress.observe(viewLifecycleOwner, {
                if (it) {
                    CustomProgressDialog.show(requireContext(), getString(R.string.processing_data))
                } else {
                    CustomProgressDialog.dismiss()
                }
            })

            viewModel.responseUpload.observe(viewLifecycleOwner, {
                it.toastUtil().show()
            })

        })

        /*
        * Checking scrollview scroll end
        * */
        bi.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
//                viewModel.loadNextPagePhotos()
            }
        }

        /*
        * vehicle search
        * */
        bi.edtSearchVehicle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                bi.edtSearchVehicle.hideKeyboard()
                val s = bi.edtSearchVehicle.text.toString()
                adapter.clearVehicleItems()
                bi.populateTxt.text = "Search: ${s.toUpperCase(Locale.ENGLISH)}"
                viewModel.searchVehicleFromDB(s)
            }
            false
        }

        /*
        * vehicle search clear
        * */
        bi.inputSearchVehicle.setEndIconOnClickListener {
            bi.edtSearchVehicle.text = null
            adapter.clearVehicleItems()
            bi.populateTxt.text = "Search: All Vehicles"
            viewModel.fetchVehiclesFromLocalDB()
        }

    }

    /*
    * Initialize recyclerView with onClickListener
    * */
    private fun callingRecyclerView() {
        adapter = VehicleListAdapter(object : VehicleListAdapter.OnItemClickListener {
            override fun onItemClick(item: VehiclesItem, attendance: Attendance?, position: Int) {
                findNavController().navigate(
                    VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailFragment(
                        item,
                        attendance
                    )
                )
            }
        })
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        bi.vehicleList.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    /*
    * Menu items
    * */
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search_menu).isVisible = true
        menu.findItem(R.id.options_group).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_menu -> {
                bi.fldGrpSearchPhotos.animate().apply {
                    duration = 1000
                    translationY(if (bi.fldGrpSearchPhotos.translationY == actionBarHeight.toFloat()) 10f else actionBarHeight.toFloat())
                }.start()
                bi.nestedScrollView.animate().apply {
                    duration = 1000
                    translationY(if (bi.nestedScrollView.translationY == actionBarHeight.toFloat() / 2) 10f else actionBarHeight.toFloat() / 2)
                }.start()

                true
            }
            R.id.download_menu -> {
                viewModel.downloadingVehicles()
                true
            }
            R.id.logout_menu -> {
                AlertDialogFragment(
                    title = getString(R.string.logout_message),
                    callBack = object : CallBack {
                        override fun actionYes() {
                            SharedStorage.setLogOutUser(sharedPrefImpl)
                            gotoActivityWithNoBackUp(LoginActivity::class.java)
                        }

                        override fun actionNo() {
                        }

                    }
                ).show(
                    this@VehicleListFragment.parentFragmentManager, AlertDialogFragment.TAG
                )
                true
            }
            R.id.upload_menu -> {
                viewModel.uploadDataToServer()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}