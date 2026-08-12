package com.example.supercartapp.model.remote

import com.example.supercartapp.model.response.CategoryResponse
import com.example.supercartapp.model.response.ProductResponse
import com.example.supercartapp.model.response.SubCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("Category")
    suspend fun getCategories(): CategoryResponse

    @GET("SubCategory")
    suspend fun getSubCategories(
        @Query("category_id") categoryId: Int
    ): SubCategoryResponse

    @GET("SubCategory/products/{sub_category_id}")
    suspend fun getProducts(
        @Path("sub_category_id") subCategoryId: Int
    ): ProductResponse
}