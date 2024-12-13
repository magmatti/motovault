package com.uken.motovault.api.retrofit.api_service

import com.uken.motovault.models.ExpenseModel
import com.uken.motovault.models.charts.ChartExpenseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ExpensesApiService {
    @GET("expenses/getAll/{mail}")
    suspend fun getExpenses(@Path("mail") mail: String): List<ExpenseModel>

    @GET("expenses/yearly/{year}/{mail}")
    suspend fun getYearlyExpenses(
        @Path("year") year: String,
        @Path("mail") mail: String
    ): List<ChartExpenseModel>

    @GET("expenses/monthly/{year}/{month}/{email}")
    suspend fun getMonthlyExpenses(
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("email") email: String
    ): List<ChartExpenseModel>

    @POST("expenses")
    suspend fun addExpense(@Body expense: ExpenseModel): ExpenseModel

    @DELETE("expenses/{id}")
    suspend fun deleteExpense(@Path("id") id: Int): Response<Unit>
}