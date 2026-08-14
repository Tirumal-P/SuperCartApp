package com.example.supercartapp.view.productdetails

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.ProductSpecificationItemBinding
import com.example.supercartapp.model.response.Specification

class ProductSpecificationViewHolder(val binding: ProductSpecificationItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(specification: Specification){
        with(binding){
            tvSpecificationsLabel.text = specification.title
            tvSpecificationsDescription.text = specification.specification
        }
    }
}