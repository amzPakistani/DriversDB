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
            uiState = try {
                _uiState.Success(driverRepository.getDrivers())
            } catch (
                e: HttpException
            ) {
                _uiState.Error
            } catch (e: UnknownHostException) {
                _uiState.Error
            } catch (e: SocketTimeoutException) {
                _uiState.Error
            } catch (e: IOException) {
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