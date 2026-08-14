package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.ApiService
import com.example.supercartapp.model.response.CategoryResponse
import com.example.supercartapp.model.response.ProductDetailsResponse
import com.example.supercartapp.model.response.ProductResponse
import com.example.supercartapp.model.response.SubCategoryResponse

class SuperCartRepositoryImpl(val apiService: ApiService): SuperCartRepository {

    override suspend fun getCategories(): CategoryResponse {
        return apiService.getCategories()
    }

    override suspend fun getSubCategories(categoryId:Int): SubCategoryResponse {
        return apiService.getSubCategories(categoryId)
    }

    override suspend fun getProducts(subCategoryId: Int): ProductResponse {
        return apiService.getProducts(subCategoryId)
    }

    override suspend fun getProductDetails(productId: Int): ProductDetailsResponse {
        return apiService.getProductDetails(productId)
    }
}