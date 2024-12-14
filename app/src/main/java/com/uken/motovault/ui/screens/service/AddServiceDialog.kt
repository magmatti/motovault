package com.uken.motovault.ui.screens.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.uken.motovault.models.ServiceModel

@Composable
fun AddServiceDialog(
    userEmail: String,
    onDismiss: () -> Unit,
    onAddService: (ServiceModel) -> Unit,
) {
    var vehicleId by remember { mutableStateOf("") }
    var serviceType by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add new service") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = vehicleId,
                    onValueChange = { vehicleId = it },
                    label = { Text("Vehicle ID") }
                )
                OutlinedTextField(
                    value = serviceType,
                    onValueChange = { serviceType = it },
                    label = { Text("Service type e.g Inspection, oil change, etc.") }
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)") }
                )
                OutlinedTextField(
                    value = total,
                    onValueChange = { total = it },
                    label = { Text("Total Amount") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val service = ServiceModel(
                        vehicleId = vehicleId.toIntOrNull() ?: 0,
                        serviceType = serviceType,
                        date = date,
                        total = total.toDoubleOrNull() ?: 0.0,
                        mail = userEmail
                    )
                    onAddService(service)
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Spacer(modifier = Modifier.padding(16.dp))
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}