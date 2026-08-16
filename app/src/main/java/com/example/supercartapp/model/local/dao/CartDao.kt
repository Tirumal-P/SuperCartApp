package com.example.supercartapp.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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

    @Transaction
    @Query("""SELECT * FROM Carts WHERE userId= :userId AND isActive LIMIT 1""")
    fun getCartWithCartItemsByUserId(userId: Long): LiveData<CartWithCartItems?>

    @Query("SELECT cartId FROM Carts WHERE userId= :userId AND isActive LIMIT 1")
    fun getActiveCartId(userId: Long): Long?
}