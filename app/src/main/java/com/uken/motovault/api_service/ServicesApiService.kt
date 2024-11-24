package com.uken.motovault.api_service

import com.uken.motovault.models.ServiceModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServicesApiService {
    @GET("services")
    suspend fun getServices(): List<ServiceModel>

    @POST("services")
    suspend fun addService(@Body service: ServiceModel): ServiceModel

    @DELETE("services/{id}")
    suspend fun deleteService(@Path("id") id: Int): Response<Unit>
}