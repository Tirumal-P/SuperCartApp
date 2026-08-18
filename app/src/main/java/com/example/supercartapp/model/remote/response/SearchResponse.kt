package com.example.supercartapp.model.remote.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("products")
    val products: List<ProductX>
)