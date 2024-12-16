package com.uken.motovault.ui.screens.service

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uken.motovault.models.ServiceModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
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
    val context = LocalContext.current

    val serviceTypes = listOf(
        "Inspection",
        "Oil service",
        "Gearbox repair",
        "Engine repair",
        "Brake replacement",
        "Clutch replacement",
        "Tire change",
        "Suspension repair",
        "Exhaust system repair",
        "Air conditioning service",
        "Windshield replacement",
        "Spark plug replacement",
        "Radiator repair",
        "Fuel system service",
        "Electrical system repair",
        "Steering system repair",
        "Turbocharger repair",
        "Interior detailing",
        "Paint touch-up",
    )
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add new service") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = serviceType,
                        onValueChange = { serviceType = it },
                        label = { Text("Expense Type") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Dropdown Icon"
                            )
                        },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        serviceTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    serviceType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                val calendar = Calendar.getInstance()
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH)
                                val day = calendar.get(Calendar.DAY_OF_MONTH)
                                DatePickerDialog(
                                    context,
                                    { _, selectedYear, selectedMonth, selectedDay ->
                                        date = "$selectedYear-${selectedMonth + 1}-${selectedDay}"
                                    },
                                    year, month, day
                                ).show()
                            }
                        ) { Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Date Picker")
                        }
                    }
                )
                var isTotalError by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = total,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() || char == '.' }) {
                            total = it
                            isTotalError = false
                        } else {
                            isTotalError = true
                        }
                    },
                    label = { Text("Total Amount") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    isError = isTotalError,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (isTotalError) {
                    Text(
                        text = "Invalid input: only numbers are allowed.",
                        color = androidx.compose.ui.graphics.Color.Red,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
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