package com.uken.motovault.retrofit

import com.uken.motovault.api_service.ExpensesApiService
import com.uken.motovault.api_service.ServicesApiService
import com.uken.motovault.api_service.VehiclesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private const val BASE_URL = "http://57.128.197.7:8080/"

    val expensesApi: ExpensesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExpensesApiService::class.java)
    }

    val servicesApi: ServicesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServicesApiService::class.java)
    }

    val vehiclesApi: VehiclesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VehiclesApiService::class.java)
    }
}