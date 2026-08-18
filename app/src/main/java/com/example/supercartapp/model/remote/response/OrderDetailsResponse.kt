package com.example.supercartapp.model.remote.response


import com.google.gson.annotations.SerializedName

data class OrderDetailsResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("order")
    val order: OrderX?
)