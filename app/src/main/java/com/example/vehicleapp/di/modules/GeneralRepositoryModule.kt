package com.example.vehicleapp.di.modules

import com.example.vehicleapp.base.repository.GeneralDataSource
import com.example.vehicleapp.base.repository.GeneralRepository
import com.example.vehicleapp.di.auth.AuthApi
import com.example.vehicleapp.di.local.VehicleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GeneralRepositoryModule {

    @Singleton
    @Provides
    fun provideGeneralDataSource(authApi: AuthApi, vehicleDao: VehicleDao): GeneralDataSource {
        return GeneralRepository(authApi, vehicleDao)
    }

}