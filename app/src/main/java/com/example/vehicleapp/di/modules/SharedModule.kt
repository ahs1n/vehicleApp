package com.example.vehicleapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author AliAzazAlam on 6/14/2021.
 */
@Module
class SharedModule {

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context, mode: Int): SharedPreferences {
        return context.getSharedPreferences(
            context.applicationContext.packageName,
            mode
        )
    }

    @Provides
    @Singleton
    fun provideSharedMode(): Int = Context.MODE_PRIVATE

}