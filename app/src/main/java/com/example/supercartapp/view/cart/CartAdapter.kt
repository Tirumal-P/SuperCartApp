package com.example.supercartapp.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.CartItemBinding
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.util.GenericDiffUtil

class CartAdapter: ListAdapter<CartItemEntity, CartViewHolder>(GenericDiffUtil({it.cartId})) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}