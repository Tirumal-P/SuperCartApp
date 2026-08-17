package com.example.supercartapp.model.local.model

import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.remote.response.ProductItem

data class ProductListUiItem(
    val product: ProductItem,
    val cartItem: CartItemEntity?
)
