package com.example.driversdb.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.driversdb.ui.DriverViewModel
import com.example.driversdb.ui._uiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(uiState:_uiState, viewModel: DriverViewModel){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember{SnackbarHostState()}
    val showDialog by viewModel.showDialog.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.showDialog()}) {
                Icon(imageVector = Icons.Default.Add , contentDescription = "Add Drivers")
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = { Text("Drivers DB") },
                scrollBehavior = scrollBehavior
            )

        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) { paddingValues ->
        HomeScreen(uiState = uiState, paddingValues = paddingValues, viewModel = viewModel, snackbarHostState = snackbarHostState)
        if(showDialog){
            DriverDialog(viewModel = viewModel)
        }
    }
}