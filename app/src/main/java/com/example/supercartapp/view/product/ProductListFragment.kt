package com.example.supercartapp.view.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentProductListBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.model.response.SubcategoryItem
import com.example.supercartapp.repository.SuperCartRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hide
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.ProductListViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ProductListFragment : Fragment(R.layout.fragment_product_list) {
    private lateinit var binding: FragmentProductListBinding
    private val productListViewModel: ProductListViewModel by viewModels {
        val repository = SuperCartRepositoryImpl(ApiClient.apiService)
        ProductListViewModel.ProductListViewModelFactory(repository)
    }

    private val args: ProductListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProductListBinding.bind(view)
        setUpObservers()
        val categoryId = args.categoryId
        productListViewModel.getSubCategories(categoryId)
    }

    private fun setUpObservers() {
        with(binding) {
            productListViewModel.subCategories.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                        pbProductListTab.hideRest(tvProductListMessage)
                    }
                    is UiState.Error ->{
                        tvProductListMessage.hideRest(pbProductListTab)
                        tvProductListMessage.text = it.message
                    }
                    is UiState.Success ->{
                        pbProductListTab.hide()
                        tvProductListMessage.hide()
                        setUpTabLayout(it.data)
                    }
                }
            }
        }
    }

    private fun setUpTabLayout(subCategoryList: List<SubcategoryItem>) {
        val subCategoryNameList = mutableListOf<String>()
        val subCategoryIdList = mutableListOf<Int>()
        subCategoryList.forEach {
            subCategoryNameList.add(it.subcategoryName)
            subCategoryIdList.add(it.subcategoryId.toInt())
        }
        with(binding){
            vpProductListPage.adapter = ProductListViewPagerAdapter(this@ProductListFragment,subCategoryIdList)
            TabLayoutMediator(tlProductListTab,vpProductListPage){tab, position->
                tab.text = subCategoryNameList[position]
            }.attach()
        }
    }

}