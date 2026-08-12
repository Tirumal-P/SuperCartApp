package com.example.supercartapp.model.response


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("products")
    val products: List<ProductItem>
)