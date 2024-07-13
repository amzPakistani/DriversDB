package com.example.driversdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.driversdb.ui.DriverViewModel
import com.example.driversdb.ui.screens.NavigationScreen
import com.example.driversdb.ui.theme.DriversDBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var viewModel: DriverViewModel

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, DriverViewModel.Factory).get(DriverViewModel::class.java)
        enableEdgeToEdge()
        setContent {
            DriversDBTheme {
                NavigationScreen(uiState = viewModel.uiState, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DriversDBTheme {
        Greeting("Android")
    }
}