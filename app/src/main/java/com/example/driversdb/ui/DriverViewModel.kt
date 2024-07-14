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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _showDeleteAlert = MutableStateFlow(false)
    val showDeleteAlert: StateFlow<Boolean> = _showDeleteAlert.asStateFlow()

    private val _showCreateAlert = MutableStateFlow(false)
    val showCreateAlert: StateFlow<Boolean> = _showCreateAlert.asStateFlow()

    private val _showUpdateAlert = MutableStateFlow(false)
    val showUpdateAlert: StateFlow<Boolean> = _showUpdateAlert.asStateFlow()


    private val _driverName = MutableStateFlow("")
    val DriverName: StateFlow<String> = _driverName.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    private val _showUpdateDialog = MutableStateFlow(false)
    val showUpdateDialog = _showUpdateDialog.asStateFlow()

    private val _driverToUpdateName = MutableStateFlow("")
    val driverToUpdateName: StateFlow<String> = _driverToUpdateName.asStateFlow()

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

    fun createDriver(driver: DriverRequest) {
        viewModelScope.launch {
            uiState = _uiState.Loading
            try {
                driverRepository.createDriver(driver)
                val updatedDrivers = driverRepository.getDrivers()
                uiState = _uiState.Success(updatedDrivers)
                Log.d("DriverViewModel", "Driver created successfully: ${driver.name}")
                _driverName.value = driver.name
            } catch (e: Exception) {
                uiState = _uiState.Error
                Log.e("DriverViewModel", "Error creating driver: ${e.message}")
            }
        }
    }

    fun updateDriver(driver: DriverRequest) {
        viewModelScope.launch {
            uiState = _uiState.Loading
            try {
                driverRepository.updateDriver(driver)
                val updatedDrivers = driverRepository.getDrivers()
                uiState = _uiState.Success(updatedDrivers)
                Log.d("DriverViewModel", "Driver updated successfully: ${driver.name}")
                _driverName.value = driver.name
            } catch (e: Exception) {
                uiState = _uiState.Error
                Log.e("DriverViewModel", "Error updating driver: ${e.message} Details: ${driver.name} ${driver.wins} ${driver.titles}")
            }
        }
    }

    fun deleteDriver(name:String?){
        viewModelScope.launch {
            try {
                driverRepository.deleteDriver(name)
                val updatedDrivers = driverRepository.getDrivers()
                uiState = _uiState.Success(updatedDrivers)
                _showDeleteAlert.value = true
                if(name!=null){
                    _driverName.value = name
                }
            } catch (e: Exception) {
                uiState = _uiState.Error
                Log.e("DriverViewModel", "Error deleting driver: ${e.message}")
            }
        }
    }

    fun showDialog(){
        _showDialog.value = true
    }

    fun hideDialog(){
        _showDialog.value = false
    }

    fun showUpdateDialog(name:String){
        _driverToUpdateName.value = name
        _showUpdateDialog.value = true
    }

    fun hideUpdateDialog(){
        _showUpdateDialog.value = false
    }

    fun resetDeleteAlert() {
        _showDeleteAlert.value = false
    }

    fun resetUpdateAlert() {
        _showUpdateDialog.value = false
    }

    fun resetCreateAlert() {
        _showCreateAlert.value = false
    }

    fun showCreateAlert() {
        _showCreateAlert.value = true
    }

    fun showUpdateAlert() {
        _showUpdateAlert.value = true
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