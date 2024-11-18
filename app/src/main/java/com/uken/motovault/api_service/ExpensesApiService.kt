package com.uken.motovault.api_service

import com.uken.motovault.models.ExpenseModel
import retrofit2.http.GET

interface ExpensesApiService {
    @GET("expenses")
    suspend fun getExpenses(): List<ExpenseModel>
}