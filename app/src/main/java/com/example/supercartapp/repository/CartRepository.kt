package com.example.supercartapp.repository
import com.example.supercartapp.model.local.entity.CartEntity
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.relation.CartWithCartItems

interface CartRepository {

    suspend fun insertCart(cartEntity: CartEntity): Long

    suspend fun updateCart(cartEntity: CartEntity): Int

    suspend fun deleteCart(cartEntity: CartEntity): Int

    suspend fun insertCartItem(cartItemEntity: CartItemEntity): Long

    suspend fun updateCartItem(cartItemEntity: CartItemEntity): Int

    suspend fun deleteCartItem(cartItemEntity: CartItemEntity): Int

    suspend fun getCartWithCartItemsByUserId(userId: Long): CartWithCartItems?
}