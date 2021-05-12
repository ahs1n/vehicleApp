package com.example.vehicleapp.ui.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.core.widget.NestedScrollView
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vehicleapp.R
import com.example.vehicleapp.adapters.VehicleListAdapter
import com.example.vehicleapp.base.FragmentBase
import com.example.vehicleapp.base.repository.ResponseStatus
import com.example.vehicleapp.base.viewmodel.VehicleViewModel
import com.example.vehicleapp.databinding.FragmentVehicleListBinding
import com.example.vehicleapp.model.VehiclesItem
import com.example.vehicleapp.ui.MainActivity
import com.example.vehicleapp.utils.hideKeyboard
import com.example.vehicleapp.utils.obtainViewModel
import com.kennyc.view.MultiStateView
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
            activity as MainActivity,
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
        viewModel.vehiclesResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    it.data?.apply {
                        adapter.vehicleItems = it.data as ArrayList<VehiclesItem>
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

                    bi.multiStateView.viewState = MultiStateView.ViewState.ERROR
                }
                ResponseStatus.LOADING -> {
                    MultiStateView.ViewState.LOADING
                }
            }

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
        * Image search
        * */
        bi.edtSearchPhotos.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                bi.edtSearchPhotos.hideKeyboard()
                val s = bi.edtSearchPhotos.text.toString()
                adapter.clearProductItem()
                bi.populateTxt.text = "Search: ${s.toUpperCase(Locale.ENGLISH)}"
                viewModel.searchImagesFromRemote(s)
            }
            false
        }

        /*
        * Image search clear
        * */
        bi.inputSearchPhotos.setEndIconOnClickListener {
            bi.edtSearchPhotos.setText("")
            adapter.clearProductItem()
            bi.populateTxt.text = "Search: Latest"
            viewModel.fetchVehiclesFromLocalDB()
        }

    }

    /*
    * Initialize recyclerView with onClickListener
    * */
    private fun callingRecyclerView() {
        adapter = VehicleListAdapter(object : VehicleListAdapter.OnItemClickListener {
            override fun onItemClick(item: VehiclesItem, position: Int) {
                viewModel.setSelectedVehicle(item)
                findNavController().navigate(VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailFragment())
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search_menu).isVisible = true
        menu.findItem(R.id.download_menu).isVisible = true
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
            else -> super.onOptionsItemSelected(item)
        }
    }
}