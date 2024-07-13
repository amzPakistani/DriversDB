package com.example.driversdb.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.driversdb.DriverApplication
import com.example.driversdb.data.DriverRepository
import com.example.driversdb.network.request.DriverRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed interface _uiState{
    data class Success(val drivers: List<DriverRequest>):_uiState
    data object Loading:_uiState
    data object Error:_uiState
}

class DriverViewModel(private val driverRepository: DriverRepository):ViewModel() {
    var uiState:_uiState by mutableStateOf(_uiState.Loading)
        private set

    init {
        getDrivers()
    }

    fun getDrivers(){
        viewModelScope.launch {
            uiState = _uiState.Loading
            try {
                val drivers = driverRepository.getDrivers()
                uiState = _uiState.Success(drivers)
            } catch (e: HttpException) {
                Log.e("DriverViewModel", "HTTP error: ${e.message()}")
                uiState = _uiState.Error
            } catch (e: UnknownHostException) {
                Log.e("DriverViewModel", "No internet connection: ${e.message}")
                uiState = _uiState.Error
            } catch (e: SocketTimeoutException) {
                Log.e("DriverViewModel", "Connection timed out: ${e.message}")
                uiState = _uiState.Error
            } catch (e: IOException) {
                Log.e("DriverViewModel", "IO error: ${e.message}")
                uiState = _uiState.Error
            } catch (e: Exception) {
                Log.e("DriverViewModel", "Error: ${e.message}")
                uiState = _uiState.Error
            }
        }
    }

    fun getDriver(name:String?){
        viewModelScope.launch {
            uiState = _uiState.Loading
            uiState = try{
                _uiState.Success(listOf(driverRepository.getDriver(name)))
            } catch (e: Exception) {
                _uiState.Error
            }
        }
    }

    fun createDriver(driver: DriverRequest){
        viewModelScope.launch {
            uiState = _uiState.Loading
            uiState = try{
                driverRepository.createDriver(driver)
                _uiState.Success(driverRepository.getDrivers())
            } catch (e: Exception) {
                _uiState.Error
            }
        }
    }

    fun deleteDriver(name:String?){
        viewModelScope.launch {
            try {
                driverRepository.deleteDriver(name)
                val updatedDrivers = driverRepository.getDrivers()
                uiState = _uiState.Success(updatedDrivers)
            } catch (e: Exception) {
                uiState = _uiState.Error
                Log.e("DriverViewModel", "Error deleting driver: ${e.message}")
                // Optionally handle the error internally without affecting the UI state
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DriverApplication
                val repository = app.container.driverRepository
                DriverViewModel(repository)
            }
        }
    }
}