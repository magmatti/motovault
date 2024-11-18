package com.uken.motovault.api_service

import com.uken.motovault.models.ServiceModel
import retrofit2.http.GET

interface ServicesApiService {
    @GET("services")
    suspend fun getServices(): List<ServiceModel>
}