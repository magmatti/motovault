package com.uken.motovault.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.models.ExpenseModel
import com.uken.motovault.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpensesViewModel: ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseModel>>(emptyList())
    val expenses: StateFlow<List<ExpenseModel>> = _expenses

    init {
        fetchExpenses()
    }

    private fun fetchExpenses() {
        viewModelScope.launch {
            try {
                val fetchedExpenses = RetrofitInstance.expensesApi.getExpenses()
                _expenses.value = fetchedExpenses
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}