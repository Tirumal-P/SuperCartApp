package com.example.supercartapp.model.response


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("image")
    val image: String,
    @SerializedName("display_order")
    val displayOrder: String
)