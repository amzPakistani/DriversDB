package com.example.driversdb.network.request

import kotlinx.serialization.Serializable

@Serializable
data class DriverRequest(
    val name:String,
    val titles:String,
    val wins:String,
)
