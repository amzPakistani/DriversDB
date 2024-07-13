package com.example.driversdb.ui.screens.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.driversdb.network.request.DriverRequest
import com.example.driversdb.ui.DriverViewModel

@Composable
fun UpdateDialog(viewModel: DriverViewModel) {

    var driverWins by remember {
        mutableStateOf("")
    }
    var driverTitle by remember {
        mutableStateOf("")
    }


    AlertDialog(
        onDismissRequest = { viewModel.hideUpdateDialog() },
        title = { Text("Update Driver", modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), textAlign = TextAlign.Start)
        },
        modifier = Modifier.fillMaxWidth(),
        text = {
            Column {
                TextField(
                    value = driverWins,
                    onValueChange = { driverWins = it },
                    label = { Text(text = "Driver's Wins") },
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)

                )
                TextField(
                    value = driverTitle,
                    onValueChange = { driverTitle = it },
                    label = { Text(text = "Driver's Titles") },
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.updateDriver(
                        DriverRequest(name = viewModel.driverToUpdateName.value, titles = driverTitle, wins = driverWins)
                    )
                    viewModel.hideUpdateDialog()
                }
            ) {
                Text(text = "Update")
            }
        },
        dismissButton = {
            Button(onClick = { viewModel.hideUpdateDialog() }) {
                Text("Cancel")
            }
        }
    )
}