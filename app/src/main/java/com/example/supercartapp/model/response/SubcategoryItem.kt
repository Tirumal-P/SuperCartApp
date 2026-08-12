package com.example.supercartapp.model.response


import com.google.gson.annotations.SerializedName

data class SubcategoryItem(
    @SerializedName("subcategory_id")
    val subcategoryId: String,
    @SerializedName("subcategory_name")
    val subcategoryName: String,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("subcategory_image_url")
    val subcategoryImageUrl: String,
    @SerializedName("is_active")
    val isActive: String
)