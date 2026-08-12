package com.example.supercartapp.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.ProductItemBinding
import com.example.supercartapp.model.response.ProductItem
import com.example.supercartapp.util.GenericDiffUtil

class ProductListAdapter :
    ListAdapter<ProductItem, ProductListViewHolder>(GenericDiffUtil<ProductItem>({ it.productId })) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductListViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}