package com.example.supercartapp.repository

import com.example.supercartapp.model.response.CategoryResponse

interface SuperCartRepository {

    suspend fun getCategories(): CategoryResponse

}