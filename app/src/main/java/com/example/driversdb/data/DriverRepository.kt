package com.example.driversdb.data

import com.example.driversdb.network.ApiService
import com.example.driversdb.network.request.DriverRequest
import com.example.driversdb.network.response.DriverResponse
import okhttp3.ResponseBody

interface DriverRepository {
    suspend fun getDrivers(): List<DriverRequest>
    suspend fun getDriver(name: String?): DriverRequest
    suspend fun createDriver(driver: DriverRequest): DriverResponse
    suspend fun deleteDriver(name: String?): ResponseBody
}

class DriverRepositoryImpl(private val apiService: ApiService):DriverRepository {
    override suspend fun getDrivers(): List<DriverRequest> = apiService.getDrivers()
    override suspend fun getDriver(name: String?): DriverRequest = apiService.getDriver(name)
    override suspend fun createDriver(driver: DriverRequest): DriverResponse = apiService.createDriver(driver)
    override suspend fun deleteDriver(name: String?): ResponseBody = apiService.deleteDriver(name)
}