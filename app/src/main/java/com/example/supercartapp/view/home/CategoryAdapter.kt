package com.example.supercartapp.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.ItemCategoryBinding
import com.example.supercartapp.model.response.CategoryItem
import com.example.supercartapp.util.GenericDiffUtil

class CategoryAdapter(val onCategoryClick:(CategoryItem)->Unit): ListAdapter<CategoryItem, CategoryItemViewHolder>(GenericDiffUtil{it.categoryId}){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryItemViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryItemViewHolder,
        position: Int
    ) {
        holder.binding.root.setOnClickListener {
            onCategoryClick(getItem(position))
        }
        holder.bind(getItem(position))
    }
}