package com.example.supercartapp.view.product

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.ProductItemBinding
import com.example.supercartapp.model.response.ProductItem

class ProductListViewHolder(val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(productItem: ProductItem){
        with(binding){
            tvProductName.text = productItem.productName
            tvProductPrice.text = "\$${productItem.price}"
            tvProductDescription.text = productItem.description
            rbProductRating.numStars = productItem.averageRating.toInt()
//            ImageGlide.glide(imgvProductImage,productItem.productImageUrl)
        }
    }
}