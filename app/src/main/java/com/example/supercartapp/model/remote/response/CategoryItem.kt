package com.example.supercartapp.model.remote.response

import com.google.gson.annotations.SerializedName

data class CategoryItem (
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("category_image_url")
    val categoryImageUrl: String,
    @SerializedName("is_active")
    val isActive: String
)