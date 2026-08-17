package com.example.supercartapp.model.remote

import com.example.supercartapp.model.remote.request.AddAddressRequest
import com.example.supercartapp.model.remote.request.LoginRequest
import com.example.supercartapp.model.remote.request.PlaceOrderRequest
import com.example.supercartapp.model.remote.response.LoginResponse
import com.example.supercartapp.model.remote.request.RegisterRequest
import com.example.supercartapp.model.remote.response.AddAddressResponse
import com.example.supercartapp.model.remote.response.RegisterResponse
import com.example.supercartapp.model.remote.response.CategoryResponse
import com.example.supercartapp.model.remote.response.AddressResponse
import com.example.supercartapp.model.remote.response.PlaceOrderResponse
import com.example.supercartapp.model.remote.response.ProductDetailsResponse
import com.example.supercartapp.model.remote.response.ProductResponse
import com.example.supercartapp.model.remote.response.SubCategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("Product/details/{product_id}")
    suspend fun getProductDetails(
        @Path("product_id") productId: Int
    ): ProductDetailsResponse

    @POST("User/auth")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("User/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @GET("User/addresses/{user_id}")
    suspend fun getUserAddresses(
        @Path("user_id") userId: Int
    ): AddressResponse

    @POST("User/address")
    suspend fun addUserAddress(
        @Body addAddressRequest: AddAddressRequest
    ): AddAddressResponse

    @POST("Order")
    suspend fun placeOrder(
        @Body orderRequest: PlaceOrderRequest
    ): PlaceOrderResponse
}