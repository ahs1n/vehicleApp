package com.example.vehicleapp.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicleapp.R
import com.example.vehicleapp.adapters.VehicleListAdapter
import com.example.vehicleapp.base.FragmentBase
import com.example.vehicleapp.base.repository.ResponseStates
import com.example.vehicleapp.base.viewmodel.VehicleViewModel
import com.example.vehicleapp.databinding.FragmentVehicleListBinding
import com.example.vehicleapp.di.shared.DefaultPreferenceManager
import com.example.vehicleapp.ui.login_activity.LoginActivity
import com.example.vehicleapp.utils.AlertDialogFragment
import com.example.vehicleapp.utils.CONSTANTS
import com.example.vehicleapp.utils.CallBack
import com.example.vehicleapp.utils.CustomProgressDialog
import com.example.vehicleapp.utils.gotoActivityWithNoBackUp
import com.example.vehicleapp.utils.showSnackBar
import com.example.vehicleapp.utils.toastUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class VehicleListFragment : FragmentBase() {

    lateinit var bi: FragmentVehicleListBinding

    private var adapter: VehicleListAdapter? = null
    private val viewModel: VehicleViewModel by activityViewModels()
    private var actionBarHeight = 0

    /*
    * Inject
    * */
    @Inject
    lateinit var sharedPref: DefaultPreferenceManager

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if(IS_SAME_USER)
//            viewModel.fetchVehiclesFromLocalDB()
//        else
//            bi.multiStateView.viewState = MultiStateView.ViewState.EMPTY

        /*
        * Initiating recyclerview
        * */
        callingRecyclerView()
        setObservers()

        viewModel.apiDownloadingDataProgress.observe(viewLifecycleOwner) {
            showProgressDialog(it)
        }

        viewModel.responseUpload.observe(viewLifecycleOwner) {
            if (it != StringUtils.EMPTY) {
                context?.toastUtil(it)?.show()
                viewModel.responseUpload.value = StringUtils.EMPTY
            }
        }

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
        /*bi.edtSearchVehicle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                bi.edtSearchVehicle.hideKeyboard()
                val s = bi.edtSearchVehicle.text.toString()
                adapter?.clearVehicleItems()
                bi.populateTxt.text = "Search: ${s.toUpperCase(Locale.ENGLISH)}"
                viewModel.searchVehicleFromDB(s)
            }
            false
        }*/
        bi.edtSearchVehicle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                adapter?.clearVehicleItems()
                bi.populateTxt.text = "Search: ${query.uppercase(Locale.ENGLISH)}"

                // Perform the search based on the user's input
                viewModel.searchVehicleFromDB(query)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        /*
        * vehicle search clear
        * */
        bi.inputSearchVehicle.setEndIconOnClickListener {
            bi.edtSearchVehicle.text = null
            adapter?.clearVehicleItems()
            bi.populateTxt.text = getString(R.string.search_latest)
            viewModel.fetchVehiclesFromLocalDB(viewModel.locationId)
        }
    }

    /*
    * Initialize recyclerView with onClickListener
    * */
    private fun callingRecyclerView() {
        adapter = VehicleListAdapter { item, attendance, position ->
            findNavController().navigate(
                VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailFragment(
                    item,
                    attendance
                )
            )
        }
        adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        bi.vehicleList.adapter = adapter
    }

    /*
    * Set Observers
    * */
    private fun setObservers() {
        lifecycleScope.launch {

            /*
            * Fetch vehicle data
            * */
            launch {
                viewModel.vehicleListDB
                    .flowWithLifecycle(lifecycle)
                    .collect {
                        when (it) {
                            is ResponseStates.Error -> {
                                showProgressDialog(false)
                                bi.root.showSnackBar(message = it.message)
                            }

                            ResponseStates.Loading -> showProgressDialog(true)
                            is ResponseStates.Success -> {
                                showProgressDialog(false)
                                if (it.data.isNullOrEmpty()) {
                                    bi.populateTxt.text = it.message
                                } else {
                                    bi.populateTxt.text = getString(R.string.search_latest)
                                    adapter?.vehicleItems = it.data
                                }
                            }
                        }
                    }
            }
        }
    }


    /*
    * Progress dialog show
    * */
    private fun showProgressDialog(flag: Boolean) {
        if (flag) {
            CustomProgressDialog.show(requireContext(), getString(R.string.downloadin_data))
        } else {
            CustomProgressDialog.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
                    positiveBtnTxt = getString(R.string.ok),
                    negativeBtnTxt = getString(R.string.cancel),
                    callBack = object : CallBack {
                        override fun actionYes() {
                            setLogOutUser()
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

    /*
    * Logout message
    * */
    private fun setLogOutUser() {
        sharedPref.put(CONSTANTS.LOGIN_FLAG, false)
        sharedPref.put(CONSTANTS.LOGIN_USERNAME, StringUtils.EMPTY)
    }
}