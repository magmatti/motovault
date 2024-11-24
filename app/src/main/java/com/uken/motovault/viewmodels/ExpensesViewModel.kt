package com.uken.motovault.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.models.ExpenseModel
import com.uken.motovault.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class ExpensesViewModel: ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseModel>>(emptyList())
    val expenses: StateFlow<List<ExpenseModel>> = _expenses

    companion object {
        const val TAG = "ExpensesViewModel"
    }

    init {
        getExpenses()
    }

    private fun getExpenses() {
        viewModelScope.launch {
            try {
                val fetchedExpenses = RetrofitInstance.expensesApi.getExpenses()
                _expenses.value = fetchedExpenses
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addExpense(expense: ExpenseModel) {
        viewModelScope.launch {
            try {
                val addedExpense = RetrofitInstance.expensesApi.addExpense(expense)
                _expenses.value = _expenses.value + addedExpense
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteExpense(id: Int) {
        viewModelScope.launch {
            try {
                val response: Response<Unit> = RetrofitInstance.expensesApi.deleteExpense(id)
                if (response.isSuccessful) {
                    getExpenses()
                } else {
                    Log.d(TAG, "deleteExpense: $response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}