package com.uken.motovault

import com.uken.motovault.api.Constants
import com.uken.motovault.api.retrofit.api_service.VehiclesApiService
import com.uken.motovault.models.VehicleModel
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VehiclesApiServiceTest {

    private val testEmail = "test@gmail.com"

    private val api: VehiclesApiService = Retrofit.Builder()
        .baseUrl(Constants.REST_API_BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(VehiclesApiService::class.java)

    @Test
    fun getVehiclesReturnsList() = runBlocking {
        val vehicles = api.getVehicles(testEmail)
        assertNotNull(vehicles)
        println("Fetched ${vehicles.size} vehicles for $testEmail")
    }

    @Test
    fun addAndDeleteVehicleFlowWorks() = runBlocking {
        // Add vehicle
        val created = api.addVehicle(
            VehicleModel(
                id = null,
                vin = "TSMEYA71S00461670",
                mail = testEmail,
                make = "Honda",
                model = "Civic"
            )
        )
        assertNotNull(created.id)
        println("Created vehicle: $created")

        // Delete vehicle
        val deleteResponse = api.deleteVehicle(created.id!!)
        assertTrue(deleteResponse.isSuccessful)
        println("Deleted vehicle with ID: ${created.id}")
    }
}
