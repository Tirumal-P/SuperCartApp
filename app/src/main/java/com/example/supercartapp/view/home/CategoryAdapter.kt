package com.example.supercartapp.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.model.response.CategoryItem
import com.example.supercartapp.util.GenericDiffUtil

class CategoryAdapter: ListAdapter<CategoryItem, CategoryItemViewHolder>(GenericDiffUtil(){it.categoryId}){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: CategoryItemViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }
}