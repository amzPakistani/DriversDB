package com.example.driversdb.data

import com.example.driversdb.network.ApiService
import com.example.driversdb.network.request.DriverRequest

interface DriverRepository {
    suspend fun getDrivers(): List<DriverRequest>
    suspend fun getDriver(name: String?): DriverRequest
}

class DriverRepositoryImpl(private val apiService: ApiService):DriverRepository {
    override suspend fun getDrivers(): List<DriverRequest> = apiService.getDrivers()
    override suspend fun getDriver(name: String?): DriverRequest = apiService.getDriver(name)
}