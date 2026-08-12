package com.example.supercartapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageGlide {
    fun glide(view: ImageView, imageEndUrl: String){
        Glide.with(view.context)
            .load("${Constants.IMAGE_BASE_URL}${imageEndUrl}")
            .into(view)
    }
}