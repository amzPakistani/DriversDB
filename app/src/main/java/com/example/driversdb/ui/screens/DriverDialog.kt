package com.example.driversdb.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.driversdb.network.request.DriverRequest
import com.example.driversdb.ui.DriverViewModel

@Composable
fun DriverDialog(viewModel: DriverViewModel) {

    var driverName by remember {
        mutableStateOf("")
    }
    var driverWins by remember {
        mutableStateOf("")
    }
    var driverTitle by remember {
        mutableStateOf("")
    }


    AlertDialog(
        onDismissRequest = { viewModel.hideDialog() },
        title = { Text("Add Driver") },
        text = {
            Column {
                OutlinedTextField(
                    value = driverName,
                    onValueChange = { driverName = it },
                    label = { Text(text = "Driver's Name") }
                )
                OutlinedTextField(
                    value = driverWins,
                    onValueChange = { driverWins = it },
                    label = { Text(text = "Driver's Wins") }
                )
                OutlinedTextField(
                    value = driverTitle,
                    onValueChange = { driverTitle = it },
                    label = { Text(text = "Driver's Titles") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.createDriver(
                        DriverRequest(name = driverName, titles = driverTitle, wins = driverWins)
                    )
                    viewModel.hideDialog()
                }
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(onClick = { viewModel.hideDialog() }) {
                Text("Cancel")
            }
        }
    )
}