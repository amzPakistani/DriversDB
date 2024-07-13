package com.example.driversdb.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.driversdb.network.request.DriverRequest



@Composable
fun ListScreen(drivers:List<DriverRequest>,modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(drivers){ driverRequest ->
            ListItem(driverRequest = driverRequest)
        }
    }
}

@Composable
fun ListItem(driverRequest: DriverRequest, modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = driverRequest.name)
        Row {
            Text(text = "${driverRequest.wins} wins, ${driverRequest.titles} titles")
        }
    }
}