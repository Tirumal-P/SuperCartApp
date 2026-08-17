package com.example.supercartapp.view.checkout.finalcart

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.FinalCartItemBinding
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.util.ImageGlide

class FinalCartViewHolder(
    val binding: FinalCartItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cartItem: CartItemEntity) {
        with(binding) {
            ImageGlide.glide(imgvProductImage, cartItem.productImageURl)
            tvProductName.text = cartItem.productName
            tvUnitPrice.text = cartItem.productPrice.toString()
            tvProductQuantity.text = cartItem.productQuantity.toString()
            tvProductPrice.text = (cartItem.productQuantity * cartItem.productPrice).toString()
        }
    }
}