package com.example.vehicleapp.di.modules

import com.example.vehicleapp.ui.fragment.VehicleDetailFragment
import com.example.vehicleapp.ui.fragment.VehicleListFragment
import com.example.vehicleapp.ui.login_activity.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Ali Azaz
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

}