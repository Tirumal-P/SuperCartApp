package com.example.supercartapp.view.home

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.ItemCategoryBinding
import com.example.supercartapp.model.remote.response.CategoryItem
import com.example.supercartapp.util.ImageGlide

class CategoryItemViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(category: CategoryItem){
        with(binding){
            tvCategoryName.text = category.categoryName
            ImageGlide.glide(imgVImage,category.categoryImageUrl)
        }
    }
}