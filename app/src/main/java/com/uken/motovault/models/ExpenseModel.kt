package com.uken.motovault.models

data class ExpenseModel(
    val id: Int,
    val vehicleId: Int,
    val expensesType: String,
    val date: String,
    val total: Double
)
