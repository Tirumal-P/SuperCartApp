package com.example.supercartapp.view.productdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.ProductReviewItemBinding
import com.example.supercartapp.model.response.Review
import com.example.supercartapp.util.GenericDiffUtil

class ProductDetailsRatingAdapter: ListAdapter<Review, ProductDetailsRatingViewHolder>(
    GenericDiffUtil<Review> { it.reviewId }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailsRatingViewHolder {
        val binding = ProductReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductDetailsRatingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductDetailsRatingViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}