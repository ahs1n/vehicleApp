package com.example.vehicleapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.vehicleapp.di.components.DaggerAppComponent
import com.example.vehicleapp.ui.login_activity.LoginActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.*
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

        //application = this
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    init {
        context = this
    }

    companion object {
        private var context: MainApp? = null


        fun applicationContext(): Context {
            return context!!.applicationContext
        }

        @SuppressLint("HardwareIds")
        // Uid generation scheme
        fun generateUid(): String {
            // Uid Scheme = 6 characters of device id + current date time in millis
            val deviceIdSS: String = LoginActivity.deviceId.substring(0, 6)
            val timeInMillis = System.currentTimeMillis()
            return String.format(Locale.getDefault(), "%s%d", deviceIdSS, timeInMillis)
        }
    }
}