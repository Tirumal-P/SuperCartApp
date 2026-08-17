package com.example.supercartapp.view.cart

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.CartItemBinding
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.util.ImageGlide

class CartViewHolder(
    val binding: CartItemBinding,
    val onIncreaseQuantity: (CartItemEntity) -> Unit,
    val onDecreaseQuantity: (CartItemEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cartItem: CartItemEntity) {
        with(binding) {
            ImageGlide.glide(imgvProductImage, cartItem.productImageURl)
            tvProductName.text = cartItem.productName
            tvProductDescription.text = cartItem.productDescription
            tvProductUnitPriceText.text = cartItem.productPrice.toString()
            tvTotalItemPrice.text = (cartItem.productPrice * cartItem.productQuantity).toString()
            tvProductQuantity.text = cartItem.productQuantity.toString()
            imgBtnIncreaseQuantity.setOnClickListener {
                onIncreaseQuantity(cartItem)
            }
            imgBtnDecreaseQuantity.setOnClickListener {
                onDecreaseQuantity(cartItem)
            }
        }
    }
}