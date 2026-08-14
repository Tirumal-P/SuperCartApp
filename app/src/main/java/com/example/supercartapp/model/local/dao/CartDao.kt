package com.example.supercartapp.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.supercartapp.model.local.entity.CartEntity
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.relation.CartWithCartItems

@Dao
interface CartDao {

    @Insert
    suspend fun insertCart(cartEntity: CartEntity): Long

    @Update
    suspend fun updateCart(cartEntity: CartEntity): Int

    @Delete
    suspend fun deleteCart(cartEntity: CartEntity): Int

    @Insert
    suspend fun insertCartItem(cartItemEntity: CartItemEntity): Long

    @Update
    suspend fun updateCartItem(cartItemEntity: CartItemEntity): Int

    @Delete
    suspend fun deleteCartItem(cartItemEntity: CartItemEntity): Int

    @Query("SELECT * FROM Carts WHERE userId= :userId LIMIT 1")
    suspend fun getCartWithCartItemsByUserId(userId: Long): CartWithCartItems?
}