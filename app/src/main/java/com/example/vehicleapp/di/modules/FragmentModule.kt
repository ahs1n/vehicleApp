package com.example.vehicleapp.di.modules

import com.example.vehicleapp.ui.fragment.VehicleDetailFragment
import com.example.vehicleapp.ui.fragment.VehicleListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Ali Azaz
 */
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindProductListFragment(): VehicleListFragment

    @ContributesAndroidInjector
    abstract fun bindProductDetailFragment(): VehicleDetailFragment
}