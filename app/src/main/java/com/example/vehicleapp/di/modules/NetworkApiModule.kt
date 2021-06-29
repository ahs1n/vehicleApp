package com.example.vehicleapp.di.modules

import com.example.vehicleapp.di.auth.AuthApi
import com.example.vehicleapp.di.auth.remote.ApiResponseCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * @author AliAzazAlam on 5/4/2021.
 */
@Module
class NetworkApiModule {

    @Singleton
    @Provides
    fun buildBackendApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun buildRetrofitClient(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(com.example.vehicleapp.BuildConfig.FLAVOR_URL)
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(150000, TimeUnit.MILLISECONDS /* milliseconds */)
            .readTimeout(100000, TimeUnit.MILLISECONDS /* milliseconds */)
            .also { item ->
                val log = HttpLoggingInterceptor()
                log.level = HttpLoggingInterceptor.Level.BODY
                item.addInterceptor(log)
                    .retryOnConnectionFailure(true)
            }.build()
    }


    @Provides
    @Singleton
    fun getGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }


    @Provides
    @Singleton
    fun getScalerConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getGsonBuilder(): Gson {
        return GsonBuilder()
            .setLenient()
            .create();
    }

}