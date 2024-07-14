package com.example.driversdb.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.driversdb.network.request.DriverRequest
import com.example.driversdb.ui.DriverViewModel
import com.example.driversdb.ui.screens.dialogs.Snackbar


@Composable
fun ListScreen(
    drivers: List<DriverRequest>,
    viewModel: DriverViewModel,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState
) {
    LazyColumn(modifier = modifier.padding(paddingValues)) {
        items(drivers) { driverRequest ->
            ListItem(
                driverRequest = driverRequest,
                onClick1 = { viewModel.deleteDriver(driverRequest.name) },
                onClick2 = { viewModel.showUpdateDialog(driverRequest.name) }
            )
        }
    }

    val showDeleteAlert by viewModel.showDeleteAlert.collectAsState()
    val showUpdateAlert by viewModel.showUpdateAlert.collectAsState()
    val showCreateAlert by viewModel.showCreateAlert.collectAsState()
    val driverName by viewModel.DriverName.collectAsState()

    if(showDeleteAlert){
        Snackbar(snackbarHostState = snackbarHostState, condition = showDeleteAlert, message = "${driverName} was deleted", dismiss = { viewModel.resetDeleteAlert() })
    }

    if(showUpdateAlert){
        Snackbar(snackbarHostState = snackbarHostState, condition = showUpdateAlert, message = "${driverName} was updated", dismiss = { viewModel.resetUpdateAlert() })
    }

    if(showCreateAlert){
        Snackbar(snackbarHostState = snackbarHostState, condition = showCreateAlert, message = "${driverName} was added", dismiss = { viewModel.resetCreateAlert() })
    }
}

@Composable
fun ListItem(
    driverRequest: DriverRequest,
    modifier: Modifier = Modifier,
    onClick1: () -> Unit = {},
    onClick2: (String) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .padding(8.dp)
                .weight(1f)) {
                Text(text = driverRequest.name, fontWeight = FontWeight.Normal, fontSize = 20.sp)
                Text(text = "${driverRequest.wins} wins, ${driverRequest.titles} titles",modifier = Modifier.padding(4.dp))
            }
            IconButton(onClick = { onClick2(driverRequest.name) }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onClick1) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}