package com.example.supercartapp.view.productdetails

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.ProductReviewItemBinding
import com.example.supercartapp.model.response.Review

class ProductDetailsRatingViewHolder(val binding: ProductReviewItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(review: Review){
        with(binding){
            userName.text = review.fullName
            reviewTitle.text = review.reviewTitle
            reviewDescription.text = review.review
            rbProductRating.rating = review.rating.toFloat()
        }
    }

}