package com.uken.motovault.api.retrofit.api_service

import com.uken.motovault.models.VehicleModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VehiclesApiService {
    @GET("vehicles/getAll/{mail}")
    suspend fun getVehicles(@Path("mail") mail: String): List<VehicleModel>

    @POST("vehicles")
    suspend fun addVehicle(@Body vehicle: VehicleModel): VehicleModel

    @DELETE("vehicles/{id}")
    suspend fun deleteVehicle(@Path("id") id: Int): Response<Unit>
}