package com.example.vehicleapp.di.auth

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author AliAzazAlam on 5/4/2021.
 */
interface AuthApi {

    @GET(ApiRoutes.GET_DATA)
    suspend fun <T> getServerData(
        @Query("table") table: String,
    ): T


}