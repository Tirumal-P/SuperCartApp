package com.example.supercartapp.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtil<T : Any>(val getId:(T)->Any): DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T , newItem: T): Boolean {
        return getId(oldItem) == getId(newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return oldItem == newItem
    }
}