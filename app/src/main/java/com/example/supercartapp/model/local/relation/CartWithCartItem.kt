package com.example.supercartapp.model.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.supercartapp.model.local.entity.CartEntity
import com.example.supercartapp.model.local.entity.CartItemEntity

data class CartWithCartItems (
    @Embedded
    val cartEntity: CartEntity,

    @Relation(
        parentColumn = "cartId",
        entityColumn = "cartId"
    )
    val cartItems: List<CartItemEntity>
)