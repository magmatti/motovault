package com.uken.motovault.ui.screens.expenses

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.uken.motovault.models.ExpenseModel
import com.uken.motovault.utilities.IntentUtilities

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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Expense") },
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
                    value = expensesType,
                    onValueChange = { expensesType = it },
                    label = { Text("Expense type e.g Petrol, Speed ticket, etc.") }
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
                onClick = { IntentUtilities.launchTextRecognitionActivity(context) }
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