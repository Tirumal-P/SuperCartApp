package com.example.supercartapp.view.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.supercartapp.model.response.Image

class ProductDetailsImageViewPager(val fragment: Fragment, val imageList: List<Image>): FragmentStateAdapter(fragment){
    override fun createFragment(position: Int): Fragment {
        val productDetailsImage = ProductDetailsImageFragment()
        val bundle = Bundle()
        bundle.putString(ProductDetailsImageFragment.IMAGE_URL,imageList[position].image)
        productDetailsImage.arguments = bundle
        return productDetailsImage
    }

    override fun getItemCount() = imageList.size
}