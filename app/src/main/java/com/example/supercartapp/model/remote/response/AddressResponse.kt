package com.example.supercartapp.model.remote.response


import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("addresses")
    val addresses: List<Address>
)