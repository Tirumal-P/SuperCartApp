package com.example.supercartapp.view.home

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.supercartapp.databinding.ItemCategoryBinding
import com.example.supercartapp.model.response.CategoryItem
import com.example.supercartapp.util.Constants

class CategoryItemViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(category: CategoryItem){
        with(binding){
            tvCategoryName.text = category.categoryName
            Glide.with(imgVImage.context)
                .load("${Constants.IMAGE_BASE_URL}${category.categoryImageUrl}")
                .into(imgVImage)
        }
    }
}