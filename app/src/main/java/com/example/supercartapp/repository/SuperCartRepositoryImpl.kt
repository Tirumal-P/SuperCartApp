package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.ApiService
import com.example.supercartapp.model.response.CategoryResponse

class SuperCartRepositoryImpl(val apiService: ApiService): SuperCartRepository {

    override suspend fun getCategories(): CategoryResponse {
        return apiService.getCategories()
    }
}