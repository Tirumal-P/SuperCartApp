package com.example.supercartapp.view.productdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.supercartapp.R
import com.example.supercartapp.databinding.ProductDetailsImageViewpagerBinding
import com.example.supercartapp.util.ImageGlide

class ProductDetailsImageFragment: Fragment(R.layout.product_details_image_viewpager) {

    lateinit var binding: ProductDetailsImageViewpagerBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProductDetailsImageViewpagerBinding.bind(view)
        setImageView()
    }

    private fun setImageView() {
        val imageUrl = arguments?.getString(IMAGE_URL)
        imageUrl.let { image->
            ImageGlide.glide(binding.imvProductImage,image.toString())
        }
    }

    companion object{
        const val IMAGE_URL = "image_url"
    }


}