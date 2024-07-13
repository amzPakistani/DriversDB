package com.example.driversdb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.driversdb.R
import com.example.driversdb.network.request.DriverRequest
import com.example.driversdb.ui.DriverViewModel
import com.example.driversdb.ui._uiState

@Composable
fun HomeScreen(uiState: _uiState) {

    when(uiState){
        is _uiState.Success -> ListScreen(uiState.drivers)
        is _uiState.Loading -> LoadingScreen()
        is _uiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = null
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "Failed To Load :/", modifier = Modifier.padding(16.dp))
    }
}