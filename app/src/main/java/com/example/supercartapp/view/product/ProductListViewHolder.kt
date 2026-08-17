package com.example.supercartapp.view.product

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.ProductItemBinding
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.model.ProductListUiItem
import com.example.supercartapp.model.remote.response.ProductItem
import com.example.supercartapp.util.ImageGlide
import com.example.supercartapp.util.hideRest

class ProductListViewHolder(val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProductListUiItem,
             onAddToCart: (ProductItem) -> Unit,
             onIncreaseQuantity: (CartItemEntity) -> Unit,
             onDecreaseQuantity: (CartItemEntity) -> Unit){
        with(binding){
            val productItem = item.product
            val cartItem = item.cartItem
            tvProductName.text = productItem.productName
            tvProductPrice.text = "\$${productItem.price}"
            tvProductDescription.text = productItem.description
            rbProductRating.rating = productItem.averageRating.toFloat()
            ImageGlide.glide(imgvProductImage,productItem.productImageUrl)
            if(cartItem == null){
                acbProductAddToCart.hideRest(llProductQuantitySelector)
            }else {
                llProductQuantitySelector.hideRest(acbProductAddToCart)
                tvQuantity.text = cartItem.productQuantity.toString()
            }
            acbProductAddToCart.setOnClickListener {
                onAddToCart(productItem)
            }
            imgBtnIncreaseQuantity.setOnClickListener {
                cartItem?.let(onIncreaseQuantity)
            }
            imgBtnDecreaseQuantity.setOnClickListener {
                cartItem?.let(onDecreaseQuantity)
            }
        }
    }
}