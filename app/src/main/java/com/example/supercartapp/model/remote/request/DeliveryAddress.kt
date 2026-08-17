package com.example.supercartapp.model.remote.request


import com.google.gson.annotations.SerializedName

data class DeliveryAddress(
    @SerializedName("title")
    val title: String,
    @SerializedName("address")
    val address: String
)