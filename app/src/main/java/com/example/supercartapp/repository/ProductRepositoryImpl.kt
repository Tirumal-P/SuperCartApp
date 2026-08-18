package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.ApiService
import com.example.supercartapp.model.remote.response.CategoryResponse
import com.example.supercartapp.model.remote.response.ProductDetailsResponse
import com.example.supercartapp.model.remote.response.ProductResponse
import com.example.supercartapp.model.remote.response.SearchResponse
import com.example.supercartapp.model.remote.response.SubCategoryResponse

class ProductRepositoryImpl(val apiService: ApiService): ProductRepository {

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

    override suspend fun searchProduct(searchText: String): SearchResponse {
        return apiService.searchProduct(searchText)
    }
}