package com.example.vehicleapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.vehicleapp.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .create(this)
            .build()
            .inject(this)

        application = this
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    companion object {
        var application: MainApp? = null
            private set

        @JvmStatic
        val context: Context
            get() = application!!.applicationContext
    }

}