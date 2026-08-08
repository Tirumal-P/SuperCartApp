package com.example.supercartapp.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.ItemCategoryBinding
import com.example.supercartapp.model.response.CategoryItem

class CategoryItemViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(category: CategoryItem){
        with(binding){
//            imgVImage.setImageResource(category.categoryImageUrl)
            tvCategoryName.text = category.categoryName
        }
    }
}