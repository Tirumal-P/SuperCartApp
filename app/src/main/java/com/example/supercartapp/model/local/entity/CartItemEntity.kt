package com.example.supercartapp.model.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("CartItems",
    foreignKeys = [
        ForeignKey(
            entity = CartEntity::class,
            parentColumns = ["cartId"],
            childColumns = ["cartId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["cartId"])
    ])
data class CartItemEntity(
    @PrimaryKey(true)
    val cartItemId: Long = 0,
    val cartId: Long,
    val productId: Long,
    val productName: String,
    val productDescription: String,
    val productImageURl: String,
    val productPrice: Int,
    val productQuantity: Int
)
