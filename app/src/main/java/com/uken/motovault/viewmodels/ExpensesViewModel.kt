package com.uken.motovault.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.api.retrofit.RetrofitInstance
import com.uken.motovault.document_generation.SpreadsheetGenerator
import com.uken.motovault.models.ExpenseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File

class ExpensesViewModel: ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseModel>>(emptyList())
    val expenses: StateFlow<List<ExpenseModel>> = _expenses

    companion object {
        const val TAG = "ExpensesViewModel"
    }

    private fun updateSortedExpenses(newExpenses: List<ExpenseModel>) {
        _expenses.value = newExpenses.sortedByDescending { it.date }
    }

    fun getExpenses(mail: String) {
        viewModelScope.launch {
            try {
                val fetchedExpenses = RetrofitInstance.expensesApi.getExpenses(mail)
                updateSortedExpenses(fetchedExpenses)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addExpense(expense: ExpenseModel) {
        viewModelScope.launch {
            try {
                val addedExpense = RetrofitInstance.expensesApi.addExpense(expense)
                updateSortedExpenses(_expenses.value + addedExpense)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateExpense(expense: ExpenseModel) {
        viewModelScope.launch {
            try {
                val updatedExpense = RetrofitInstance.expensesApi.updateExpense(
                    expense.id!!,
                    expense
                )
                val updatedList = _expenses.value.map {
                    if (it.id == updatedExpense.id) updatedExpense else it
                }
                updateSortedExpenses(updatedList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteExpense(id: Int, mail: String) {
        viewModelScope.launch {
            try {
                val response: Response<Unit> = RetrofitInstance.expensesApi.deleteExpense(id)
                if (response.isSuccessful) {
                    getExpenses(mail)
                } else {
                    Log.d(TAG, "deleteExpense: $response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun generateSpreadsheet(context: Context): File? {
        val expensesList = expenses.value
        val spreadsheetGenerator = SpreadsheetGenerator()

        return spreadsheetGenerator.generateExpenseReport(context, expensesList)
    }
}