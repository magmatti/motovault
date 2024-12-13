package com.uken.motovault.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
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
import com.uken.motovault.models.VehicleModel

@Composable
fun AddVehicleDialog(
    onDismiss: () -> Unit,
    onAddVehicle: (VehicleModel) -> Unit,
    email: String
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
                onClick = {
                    val vehicle = VehicleModel(
                        vin = vinNumber,
                        mail = email,
                        id = null
                    )
                    onAddVehicle(vehicle)
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}