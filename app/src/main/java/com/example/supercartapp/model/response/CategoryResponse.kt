package com.example.supercartapp.model.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("categories")
    val categories: List<CategoryItem>
)