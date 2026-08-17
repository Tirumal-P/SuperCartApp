package com.example.supercartapp.view.checkout.finalcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.FinalCartItemBinding
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.util.GenericDiffUtil

class FinalCartAdapter : ListAdapter<CartItemEntity, FinalCartViewHolder>(GenericDiffUtil({ it.cartId })) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FinalCartViewHolder {
        val binding = FinalCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinalCartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FinalCartViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))

    }
}