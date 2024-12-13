package com.uken.motovault.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uken.motovault.api.retrofit.RetrofitInstance
import com.uken.motovault.models.charts.ChartExpenseModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InsightsViewModel: ViewModel() {

    private val _yearlyExpenses = MutableStateFlow<List<ChartExpenseModel>>(emptyList())
    val yearlyExpenses: StateFlow<List<ChartExpenseModel>> = _yearlyExpenses

    private val _monthlyExpenses = MutableStateFlow<List<ChartExpenseModel>>(emptyList())
    val monthlyExpenses: StateFlow<List<ChartExpenseModel>> = _monthlyExpenses

    companion object {
        const val TAG = "InsightsViewModel"
    }

    fun getYearlyExpenses(year: String, mail: String) {
        viewModelScope.launch {
            try {
                val fetchedExpensesByYear =
                    RetrofitInstance.expensesApi.getYearlyExpenses(year, mail)
                _yearlyExpenses.value = fetchedExpensesByYear
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getMonthlyExpenses(year: String, month: String, email: String) {
        viewModelScope.launch {
            try {
                val fetchedMonthlyExpenses =
                    RetrofitInstance.expensesApi.getMonthlyExpenses(year, month, email)
                _monthlyExpenses.value = fetchedMonthlyExpenses
                Log.d(TAG, "getMonthlyExpenses: $fetchedMonthlyExpenses")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}