package com.example.driversdb

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.driversdb.ui.DriverViewModel
import com.example.driversdb.ui._uiState
import com.example.driversdb.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(uiState:_uiState, viewModel: DriverViewModel){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            LargeTopAppBar(
                title = { Text("Drivers DB") },
                scrollBehavior = scrollBehavior
            )

        }
    ) { paddingValues ->
        HomeScreen(uiState = uiState, paddingValues = paddingValues, viewModel = viewModel)
    }
}