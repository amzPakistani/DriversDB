package com.example.driversdb

import android.app.Application
import com.example.driversdb.data.AppContainer
import com.example.driversdb.data.DefaultAppContainer

class DriverApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}