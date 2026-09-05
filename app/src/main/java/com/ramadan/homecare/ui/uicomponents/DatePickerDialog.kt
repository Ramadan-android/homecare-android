package com.ramadan.homecare.ui.uicomponents

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onClickDismiss: () -> Unit,
    onClickSet: () -> Unit,
    state: DatePickerState
) {
    DatePickerDialog (
        onDismissRequest = onClickDismiss,
        confirmButton = {
            TextButton(
                onClick = onClickSet
            ) {
                Text("set")
            }
        },
        dismissButton = {
            TextButton(onClick = onClickDismiss) {
                Text("cancel")
            }
        }
    ) {
        DatePicker(state = state)
    }
}

