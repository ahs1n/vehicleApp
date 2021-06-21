package com.example.vehicleapp.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vehicleapp.di.shared.SharedStorageBase
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class FragmentBase : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPrefImpl: SharedStorageBase

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}