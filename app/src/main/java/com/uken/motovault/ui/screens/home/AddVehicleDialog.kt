package com.uken.motovault.ui.screens.home

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.uken.motovault.VinScanningActivity
import com.uken.motovault.models.VehicleModel

@Composable
fun AddVehicleDialog(
    onDismiss: () -> Unit,
    onAddVehicle: (VehicleModel) -> Unit,
    email: String
) {
    var vinNumber by remember { mutableStateOf("") }
    var isVinValid by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val vin = result.data?.getStringExtra("vin") ?: ""
            vinNumber = vin
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add Vehicle")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = vinNumber,
                    onValueChange = {
                        vinNumber = it
                        isVinValid = it.length == 17
                    },
                    label = { Text("VIN Number") },
                    isError = !isVinValid,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!isVinValid) {
                    Text(
                        text = "VIN Number must be exactly 17 characters long",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isVinValid) {
                        val vehicle = VehicleModel(
                            vin = vinNumber,
                            mail = email,
                            id = null,
                            make = null,
                            model = null
                        )
                        onAddVehicle(vehicle)
                    }
                },
                enabled = isVinValid
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Row {
                TextButton(
                    onClick = {
                        val intent = Intent(context, VinScanningActivity::class.java)
                        launcher.launch(intent)
                    }
                ) {
                    Icon(Icons.Filled.DocumentScanner, "DocumentScanner")
                    Text("Scan")
                }
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}