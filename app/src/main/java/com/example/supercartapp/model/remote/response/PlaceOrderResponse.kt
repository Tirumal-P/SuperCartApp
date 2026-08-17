package com.example.supercartapp.model.remote.response

import com.google.gson.annotations.SerializedName

data class PlaceOrderResponse(
    val status: Int,
    val message: String,
    @SerializedName("order_id")
    val orderId: Int?
)