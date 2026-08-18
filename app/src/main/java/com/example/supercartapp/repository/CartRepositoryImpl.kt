package com.example.supercartapp.repository

import androidx.lifecycle.LiveData
import com.example.supercartapp.model.local.dao.CartDao
import com.example.supercartapp.model.local.entity.CartEntity
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.relation.CartWithCartItems

class CartRepositoryImpl(val cartDao: CartDao): CartRepository {
    override suspend fun insertCart(cartEntity: CartEntity): Long {
        return cartDao.insertCart(cartEntity)
    }

    override suspend fun updateCart(cartEntity: CartEntity): Int {
        return cartDao.updateCart(cartEntity)
    }

    override suspend fun deleteCart(cartEntity: CartEntity): Int {
        return cartDao.deleteCart(cartEntity)
    }

    override suspend fun insertCartItem(cartItemEntity: CartItemEntity): Long {
        return cartDao.insertCartItem(cartItemEntity)
    }

    override suspend fun updateCartItem(cartItemEntity: CartItemEntity): Int {
        return cartDao.updateCartItem(cartItemEntity)
    }

    override suspend fun deleteCartItem(cartItemEntity: CartItemEntity): Int {
        return cartDao.deleteCartItem(cartItemEntity)
    }

    override fun getCartWithCartItemsByUserId(userId: Long): LiveData<CartWithCartItems?> {
        return cartDao.getCartWithCartItemsByUserId(userId)
    }

    override suspend fun getActiveCartById(userId: Long): Long? {
        return cartDao.getActiveCartId(userId)
    }

    override suspend fun ensureActiveCart(userId: Long) {
        val activeCart = cartDao.getActiveCartId(userId)
        if(activeCart == null){
            cartDao.insertCart(CartEntity(userId= userId))
        }
    }

    override suspend fun makeCartInactive(cartId: Long): Int {
        return cartDao.makeCartInactive(cartId)
    }

}