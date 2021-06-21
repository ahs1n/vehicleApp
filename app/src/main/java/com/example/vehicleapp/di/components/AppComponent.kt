package com.example.vehicleapp.di.components

import android.app.Application
import android.content.SharedPreferences
import com.example.vehicleapp.MainApp
import com.example.vehicleapp.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkApiModule::class,
        ApplicationModule::class,
        DatabaseModule::class,
        FragmentModule::class,
        ActivityModule::class,
        GeneralRepositoryModule::class,
        ViewModelFactoryModule::class,
        SharedModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: MainApp)
}