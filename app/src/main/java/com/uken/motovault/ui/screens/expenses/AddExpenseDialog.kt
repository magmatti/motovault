package com.uken.motovault.ui.screens.expenses

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.DocumentScanner
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uken.motovault.ReceiptScanningActivity
import com.uken.motovault.models.ExpenseModel
import java.util.Calendar

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(
    context: Context,
    userEmail: String?,
    onDismiss: () -> Unit,
    onAddExpense: (ExpenseModel) -> Unit
) {
    var vehicleId by remember { mutableStateOf("") }
    var expensesType by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }
    val expenseTypes = listOf("Petrol", "Speed Ticket", "Insurance", "Detailing")
    var expanded by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val suma = result.data?.getStringExtra("suma") ?: ""
            val recognizedDate = result.data?.getStringExtra("data") ?: ""
            total = suma
            date = recognizedDate
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Expense") },
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
                        value = expensesType,
                        onValueChange = { expensesType = it },
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
                        expenseTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    expensesType = type
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
                                        date = String.format(
                                            "%04d-%02d-%02d",
                                            selectedYear,
                                            selectedMonth + 1,
                                            selectedDay
                                        )
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
                    val expense = ExpenseModel(
                        vehicleId = vehicleId.toIntOrNull() ?: 0,
                        expensesType = expensesType,
                        date = date,
                        total = total.toDoubleOrNull() ?: 0.0,
                        mail = userEmail
                    )
                    onAddExpense(expense)
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    val intent = Intent(context, ReceiptScanningActivity::class.java)
                    launcher.launch(intent)
                }
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