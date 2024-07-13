package com.example.driversdb.data

import com.example.driversdb.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val driverRepository:DriverRepository
}

class DefaultAppContainer:AppContainer{
    val BASE_URL = "http://10.0.2.2:8080/"
    val json = Json{
        this.ignoreUnknownKeys = true
        coerceInputValues = true
    }
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    override val driverRepository: DriverRepository by lazy {
        DriverRepositoryImpl(retrofitService)
    }
}