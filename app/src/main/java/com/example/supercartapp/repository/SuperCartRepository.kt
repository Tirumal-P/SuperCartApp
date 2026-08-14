package com.example.supercartapp.repository

import com.example.supercartapp.model.response.CategoryResponse
import com.example.supercartapp.model.response.ProductDetailsResponse
import com.example.supercartapp.model.response.ProductResponse
import com.example.supercartapp.model.response.SubCategoryResponse

interface SuperCartRepository {

    suspend fun getCategories(): CategoryResponse

    suspend fun getSubCategories(categoryId: Int): SubCategoryResponse

    suspend fun getProducts(subCategoryId: Int): ProductResponse

    suspend fun getProductDetails(productId: Int): ProductDetailsResponse
}