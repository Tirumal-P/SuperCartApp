package com.example.supercartapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.supercartapp.R

object ImageGlide {
    fun glide(view: ImageView, imageEndUrl: String){
        Glide.with(view.context)
            .load("${Constants.IMAGE_BASE_URL}${imageEndUrl}")
            .error(R.drawable.error_image)
            .into(view)
    }
}