package com.uken.motovault.ui.screens.insights

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthSelectorSheet(
    sheetState: SheetState,
    showSheet: MutableState<Boolean>,
    onMonthSelected: (String, Int) -> Unit
) {
    val months = listOf(
        "January" to 1, "February" to 2, "March" to 3, "April" to 4,
        "May" to 5, "June" to 6, "July" to 7, "August" to 8,
        "September" to 9, "October" to 10, "November" to 11, "December" to 12
    )

    ModalBottomSheet(
        onDismissRequest = { showSheet.value = false },
        sheetState = sheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            months.forEach { (monthName, monthNumber) ->
                androidx.compose.material3.Text(
                    text = monthName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            onMonthSelected(monthName, monthNumber)
                            showSheet.value = false
                        }
                )
            }
        }
    }
}
