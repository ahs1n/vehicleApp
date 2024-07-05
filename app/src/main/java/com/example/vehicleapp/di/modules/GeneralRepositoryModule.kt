package com.example.vehicleapp.di.modules

import android.content.Context
import com.example.vehicleapp.base.repository.GeneralDataSource
import com.example.vehicleapp.base.repository.GeneralRepository
import com.example.vehicleapp.di.auth.AuthApi
import com.example.vehicleapp.di.local.VehicleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GeneralRepositoryModule {

    @Singleton
    @Provides
    fun provideGeneralDataSource(
        authApi: AuthApi,
        vehicleDao: VehicleDao
    ): GeneralDataSource {
        return GeneralRepository(authApi, vehicleDao)
    }

}