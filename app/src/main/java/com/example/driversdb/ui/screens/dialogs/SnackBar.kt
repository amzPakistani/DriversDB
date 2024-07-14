package com.example.driversdb.ui.screens.dialogs

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.driversdb.ui.DriverViewModel

@Composable
fun Snackbar(
    snackbarHostState: SnackbarHostState,
    condition:Boolean,
    message:String,
    dismiss:() -> Unit
){

    LaunchedEffect(key1 = condition) {
        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = "Dismiss",
            duration = SnackbarDuration.Long
        )
        if (result == SnackbarResult.ActionPerformed) {
            snackbarHostState.currentSnackbarData?.dismiss()
            dismiss()
        }
    }
}