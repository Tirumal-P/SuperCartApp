package com.example.supercartapp.model.remote.response


import com.google.gson.annotations.SerializedName

data class Specification(
    @SerializedName("specification_id")
    val specificationId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("specification")
    val specification: String,
    @SerializedName("display_order")
    val displayOrder: String
)