package com.example.supercartapp.model.local.model

data class CartProduct(
    val productId: Long,
    val productName: String,
    val description: String,
    val price: Int,
    val imageUrl: String
)
