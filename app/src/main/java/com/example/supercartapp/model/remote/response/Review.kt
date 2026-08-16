package com.example.supercartapp.model.remote.response


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("review_id")
    val reviewId: String,
    @SerializedName("review_title")
    val reviewTitle: String,
    @SerializedName("review")
    val review: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("review_date")
    val reviewDate: String
)