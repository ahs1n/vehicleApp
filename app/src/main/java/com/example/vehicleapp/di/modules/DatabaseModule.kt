package com.example.vehicleapp.di.modules

import android.content.Context
import com.example.vehicleapp.di.local.VehicleAppDatabase
import com.example.vehicleapp.di.local.VehicleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author ali azaz on 5/7/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideVehicleAppDatabase(@ApplicationContext context: Context): VehicleAppDatabase {
        return VehicleAppDatabase.invoke(context)
    }

    @Singleton
    @Provides
    fun getDAO(vehicleAppDatabase: VehicleAppDatabase): VehicleDao {
        return vehicleAppDatabase.vehicleDao()
    }

}