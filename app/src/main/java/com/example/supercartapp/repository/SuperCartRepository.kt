package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.response.CategoryResponse
import com.example.supercartapp.model.remote.response.ProductDetailsResponse
import com.example.supercartapp.model.remote.response.ProductResponse
import com.example.supercartapp.model.remote.response.SubCategoryResponse

interface SuperCartRepository {

    suspend fun getCategories(): CategoryResponse

    suspend fun getSubCategories(categoryId: Int): SubCategoryResponse

    suspend fun getProducts(subCategoryId: Int): ProductResponse

    suspend fun getProductDetails(productId: Int): ProductDetailsResponse
}