package com.uken.motovault

import com.uken.motovault.api.Constants
import com.uken.motovault.api.retrofit.api_service.ServicesApiService
import com.uken.motovault.models.ServiceModel
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServicesApiServiceTest {

    private val testEmail = "test@gmail.com"
    private val testVehicleId = 0

    private val api: ServicesApiService = Retrofit.Builder()
        .baseUrl(Constants.REST_API_BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServicesApiService::class.java)

    @Test
    fun getServicesReturnsNonEmptyListOrEmptyButNotNull() = runBlocking {
        val services = api.getServices(testEmail)
        assertNotNull(services)
        println("Fetched ${services.size} services for $testEmail")
    }

    @Test
    fun addUpdateDeleteServiceFlowWorks() = runBlocking {
        val created = api.addService(
            ServiceModel(
                id = null,
                vehicleId = testVehicleId,
                serviceType = "Oil Change",
                date = "2025-06-01",
                total = 89.99,
                mail = testEmail
            )
        )
        assertNotNull(created.id)
        println("Created service: $created")

        // Step 2: Update the created service
        val updated = api.updateService(
            created.id!!,
            created.copy(
                serviceType = "Updated Oil Change",
                total = 99.99,
            )
        )
        assertEquals("Updated Oil Change", updated.serviceType)
        assertEquals(99.99, updated.total, 0.01)
        println("Updated service: $updated")

        // Step 3: Delete the updated service
        val deleteResponse = api.deleteService(updated.id!!)
        assertTrue(deleteResponse.isSuccessful)
        println("Deleted service with ID: ${updated.id}")
    }
}
