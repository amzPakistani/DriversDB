package com.example.driversdb.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driversdb.data.DriverRepository
import com.example.driversdb.network.request.DriverRequest
import kotlinx.coroutines.launch

sealed interface _uiState{
    data class Success(val drivers: List<DriverRequest>):_uiState
    data object Loading:_uiState
    data object Error:_uiState
}

class DriverViewModel(private val driverRepository: DriverRepository):ViewModel() {
    var uiState:_uiState by mutableStateOf(_uiState.Loading)
        private set

    fun getDrivers(){
        viewModelScope.launch {
            uiState = _uiState.Loading
            uiState = try {
                _uiState.Success(driverRepository.getDrivers())
            } catch (e: Exception) {
                _uiState.Error
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
}