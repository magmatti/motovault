package com.uken.motovault.ui.screens.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hd.charts.PieChartView
import com.hd.charts.common.model.ChartDataSet
import com.hd.charts.style.PieChartDefaults
import com.uken.motovault.models.charts.ChartExpenseModel

@Composable
fun ExpensePieChart(title: String, items: List<ChartExpenseModel>) {
    val values = items.map { it.value }
    val labels = items.map { "${it.category}: ${it.value} PLN" }
    val pieColors = generateColors(items.size)

    val dataSet = ChartDataSet(
        items = values,
        title = title,
        postfix = " PLN"
    )

    val style = PieChartDefaults.style(
        borderColor = Color.Black,
        donutPercentage = 40f,
        borderWidth = 10f,
        pieColors = pieColors,
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        PieChartView(
            dataSet = dataSet,
            style = style
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            labels.forEachIndexed { index, label ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                            .background(pieColors[index % pieColors.size])
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

private fun generateColors(size: Int): List<Color> {
    val baseColors = listOf(
        Color(0xFF6A5ACD),
        Color(0xFF4682B4),
        Color(0xFF87CEEB),
        Color(0xFFB0C4DE),
        Color(0xFF778899)
    )

    return List(size) { index ->
        baseColors[index % baseColors.size]
    }
}