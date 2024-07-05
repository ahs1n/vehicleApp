package com.example.vehicleapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.vehicleapp.di.shared.DefaultPreferenceManager
import com.example.vehicleapp.di.shared.PrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author AliAzazAlam on 6/14/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class SharedModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context, mode: Int): SharedPreferences {
        return context.getSharedPreferences(
            context.applicationContext.packageName,
            mode
        )
    }

    @Provides
    @Singleton
    fun provideSharedMode(): Int = Context.MODE_PRIVATE

    @Provides
    @Singleton
    fun provideStorageBase(sharedPreferences: SharedPreferences): PrefManager =
        DefaultPreferenceManager(sharedPreferences)

}