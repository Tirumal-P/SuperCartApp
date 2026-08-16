package com.example.supercartapp.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Carts")
data class CartEntity(

    @PrimaryKey(true)
    val cartId: Long = 0,
    val userId: Long,
    val isActive: Boolean = true
)
