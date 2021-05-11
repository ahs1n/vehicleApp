package com.example.vehicleapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author AliAzazAlam on 5/12/2021.
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun providesApplicationContext(application: Application): Context = application

}