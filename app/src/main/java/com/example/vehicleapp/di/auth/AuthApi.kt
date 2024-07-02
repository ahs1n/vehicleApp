package com.example.vehicleapp.di.auth

import com.example.vehicleapp.di.auth.remote.ApiResponse
import com.example.vehicleapp.model.Attendance
import com.example.vehicleapp.model.response.ServerUploadReturn
import com.example.vehicleapp.model.utils.Users
import com.example.vehicleapp.model.utils.Vehicles
import com.example.vehicleapp.utils.CONSTANTS
import org.apache.commons.lang3.StringUtils
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author AliAzazAlam on 5/4/2021.
 */
interface AuthApi {

    @GET(ApiRoutes.GET_DATA)
    suspend fun getVehicleServerData(
        @Query("table") table: String,
        @Query("where") where: String
    ): ApiResponse<Vehicles>

    @GET(ApiRoutes.GET_DATA + "/" + CONSTANTS.USER_TABLE)
    suspend fun getUserServerData(): ApiResponse<Users>

    @POST(ApiRoutes.UPLOAD_DATA)
    suspend fun uploadAttendanceDataToServer(
        @Query("table") table: String = CONSTANTS.ATTENDANCE_TABLE,
        @Body attendance: List<Attendance>
    ): ApiResponse<ServerUploadReturn>


}