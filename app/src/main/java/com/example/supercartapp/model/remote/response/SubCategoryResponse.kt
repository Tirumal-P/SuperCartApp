package com.example.supercartapp.model.remote.response


import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("subcategories")
    val subcategories: List<SubcategoryItem>
)