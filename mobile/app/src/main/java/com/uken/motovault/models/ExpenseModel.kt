package com.uken.motovault.models

data class ExpenseModel(
    val id: Int? = null,
    val vehicleId: Int,
    val expensesType: String,
    val date: String,
    val total: Double,
    val mail: String?
)
