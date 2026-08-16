package com.example.supercartapp.view.productdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.ProductSpecificationItemBinding
import com.example.supercartapp.model.remote.response.Specification
import com.example.supercartapp.util.GenericDiffUtil

class ProductDetailsSpecificationAdapter: ListAdapter<Specification, ProductSpecificationViewHolder>(
    GenericDiffUtil<Specification> { it.specificationId }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductSpecificationViewHolder {
        val binding = ProductSpecificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductSpecificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductSpecificationViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}