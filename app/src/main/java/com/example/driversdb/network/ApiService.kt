package com.example.driversdb.network

import com.example.driversdb.network.request.DriverRequest
import com.example.driversdb.network.response.DriverResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("get-driver")
    suspend fun getDriver(@Query("name") name: String?): DriverRequest

    @GET("get-drivers")
    suspend fun getDrivers(): List<DriverRequest>

//    @POST("create-driver")
//    suspend fun createDriver(@Body driver: DriverRequest): DriverResponse
}