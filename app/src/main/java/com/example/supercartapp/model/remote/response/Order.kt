package com.example.supercartapp.model.remote.response


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("address_title")
    val addressTitle: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("bill_amount")
    val billAmount: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("order_status")
    val orderStatus: String,
    @SerializedName("order_date")
    val orderDate: String
)