package com.example.vehicleapp.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.databinding.library.baseAdapters.BR
import com.example.vehicleapp.R
import com.example.vehicleapp.base.FragmentBase
import com.example.vehicleapp.base.repository.ResponseStatus
import com.example.vehicleapp.base.viewmodel.VehicleViewModel
import com.example.vehicleapp.databinding.FragmentVehicleDetailBinding
import com.example.vehicleapp.ui.MainActivity
import com.example.vehicleapp.utils.obtainViewModel


class VehicleDetailFragment : FragmentBase() {

    lateinit var viewModel: VehicleViewModel
    lateinit var bi: FragmentVehicleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false);
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search_menu).isVisible = false
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
        * Fetch product list
        * */
        viewModel.selectedVehicleResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    val data = it.data
                    data?.let { item ->

                        /*
                        * Passing data to view
                        * */
                        bi.setVariable(BR.vehicleItem, item)

                        /*
                        * Show loading and wait until view is not ready
                        * */
                        /*lifecycleScope.launch {
                            delay(500)
                            bi.productImg.visibility = View.VISIBLE
                        }*/
                    }
                }
                ResponseStatus.ERROR -> {
                }
                ResponseStatus.LOADING -> {
                }
            }

        })
    }

}