package com.example.supercartapp.view.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProductListViewPagerAdapter(val fragment: Fragment, val subCategoryIdList: List<Int>): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        val productListPageFragment = ProductListPageFragment()
        val bundle = Bundle()
        bundle.putInt(ProductListPageFragment.ARG_SUBCATEGORY_ID,subCategoryIdList[position])
        productListPageFragment.arguments = bundle
        return productListPageFragment
    }

    override fun getItemCount() = subCategoryIdList.size

}