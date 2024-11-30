package com.uken.motovault.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddVehicleDialog(
    onDismiss: () -> Unit,
//    onAddService: (ServiceModel) -> Unit, to be added later
) {
    var vehicleName by remember { mutableStateOf("") }
    var vinNumber by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add Vehicle")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = vehicleName,
                    onValueChange = { vehicleName = it },
                    label = { Text("Vehicle Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = vinNumber,
                    onValueChange = { vinNumber = it },
                    label = { Text("VIN Number") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { /* To do */ }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { /* To do */ }
            ) {
                Icon(Icons.Filled.DocumentScanner, "DocumentScanner")
                Text("Scan")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}