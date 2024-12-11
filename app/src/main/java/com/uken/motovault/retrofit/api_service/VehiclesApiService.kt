package com.uken.motovault.retrofit.api_service

import com.uken.motovault.models.VehicleModel
import retrofit2.http.GET

interface VehiclesApiService {
    @GET("vehicles")
    suspend fun getVehicles(): List<VehicleModel>
}