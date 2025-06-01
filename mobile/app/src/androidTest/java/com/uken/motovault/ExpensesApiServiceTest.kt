package com.uken.motovault

import com.uken.motovault.api.retrofit.api_service.ExpensesApiService
import com.uken.motovault.api.Constants
import com.uken.motovault.models.ExpenseModel
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExpensesApiServiceTest {

    private val testEmail = "test@gmail.com"
    private val testVehicleId = 0
    private val testYear = "2025"
    private val testMonth = "06"

    private val api: ExpensesApiService = Retrofit.Builder()
        .baseUrl(Constants.REST_API_BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ExpensesApiService::class.java)

    @Test
    fun getExpensesReturnsNonNullList() = runBlocking {
        val expenses = api.getExpenses(testEmail)
        assertNotNull(expenses)
        println("Fetched ${expenses.size} expenses")
    }

    @Test
    fun getYearlyExpensesReturnsValidData() = runBlocking {
        val result = api.getYearlyExpenses(testYear, testEmail)
        assertNotNull(result)
        println("Fetched ${result.size} yearly expense entries")
    }

    @Test
    fun getMonthlyExpensesReturnsValidData() = runBlocking {
        val result = api.getMonthlyExpenses(testYear, testMonth, testEmail)
        assertNotNull(result)
        println("Fetched ${result.size} monthly expense entries")
    }

    @Test
    fun addUpdateDeleteExpenseWorks() = runBlocking {
        // Add
        val created = api.addExpense(
            ExpenseModel(
                id = null,
                vehicleId = testVehicleId,
                expensesType = "Fuel",
                date = "2025-06-01",
                total = 49.99,
                mail = testEmail
            )
        )
        assertNotNull(created.id)
        println("Created expense: $created")

        // Update
        val updated = api.updateExpense(
            created.id!!,
            created.copy(total = 59.99, expensesType = "UpdatedFuel")
        )
        assertEquals("UpdatedFuel", updated.expensesType)
        assertEquals(59.99, updated.total, 0.01)
        println("Updated expense: $updated")

        // Delete
        val deleteResponse = api.deleteExpense(created.id)
        assertTrue(deleteResponse.isSuccessful)
        println("Deleted expense with ID: ${created.id}")
    }
}
