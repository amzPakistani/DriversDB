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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.driversdb.network.request.DriverRequest
import com.example.driversdb.ui.DriverViewModel


@Composable
fun ListScreen(
    drivers: List<DriverRequest>,
    viewModel: DriverViewModel,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    LazyColumn(modifier = modifier.padding(paddingValues)) {
        items(drivers) { driverRequest ->
            ListItem(
                driverRequest = driverRequest,
                onClick = { viewModel.deleteDriver(driverRequest.name) }
            )
        }
    }
}

@Composable
fun ListItem(
    driverRequest: DriverRequest,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(8.dp).weight(1f)) {
                Text(text = driverRequest.name)
                Text(text = "${driverRequest.wins} wins, ${driverRequest.titles} titles")
            }
            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}