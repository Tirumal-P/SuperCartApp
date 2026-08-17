package com.example.supercartapp.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.ProductItemBinding
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.model.ProductListUiItem
import com.example.supercartapp.model.remote.response.ProductItem
import com.example.supercartapp.util.GenericDiffUtil

class ProductListAdapter(
    val onProductClick: (ProductItem) -> Unit,
    val onAddToCart: (ProductItem) -> Unit,
    val onIncreaseQuantity: (CartItemEntity) -> Unit,
    val onDecreaseQuantity: (CartItemEntity) -> Unit,
) :
    ListAdapter<ProductListUiItem, ProductListViewHolder>(GenericDiffUtil<ProductListUiItem>({ it.product.productId })) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.binding.root.setOnClickListener { onProductClick(item.product) }
        holder.bind(item,
            onAddToCart,
            onIncreaseQuantity,
            onDecreaseQuantity)
    }
}