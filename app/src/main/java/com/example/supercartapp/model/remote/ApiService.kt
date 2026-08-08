package com.example.supercartapp.model.remote

import com.example.supercartapp.model.response.CategoryResponse
import retrofit2.http.GET

interface ApiService {

    @GET("Category")
    suspend fun getCategories(): CategoryResponse
}