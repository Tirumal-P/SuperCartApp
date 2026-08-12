package com.example.supercartapp.view.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentProductListPageBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.repository.SuperCartRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hide
import com.example.supercartapp.util.show
import com.example.supercartapp.viewmodel.ProductListViewModel

class ProductListPageFragment : Fragment(R.layout.fragment_product_list_page) {

    private lateinit var binding: FragmentProductListPageBinding
    private val productListViewModel: ProductListViewModel by viewModels{
        val repository = SuperCartRepositoryImpl(ApiClient.apiService)
        ProductListViewModel.ProductListViewModelFactory(repository)
    }

    private lateinit var productListAdapter: ProductListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProductListPageBinding.bind(view)
        setUpView()
    }

    private fun setUpView() {
        with(binding){

            val subCategoryId = arguments?.getInt(ARG_SUBCATEGORY_ID)
            if(subCategoryId != null){
                setUpObserver()
                productListViewModel.getProducts(subCategoryId)
                productListAdapter = ProductListAdapter()
                rvProductList.apply {
                    adapter = productListAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }else{
                tvProductListPageMessage.show()
                pbProductPageProgress.hide()
                rvProductList.hide()
                tvProductListPageMessage.text = "Error in Fetching Subcategory Id"
            }
        }
    }

    fun setUpObserver() {
        with(binding){
            productListViewModel.products.observe(viewLifecycleOwner){productList->
                when(productList){
                    is UiState.Loading -> {
                        pbProductPageProgress.show()
                        tvProductListPageMessage.hide()
                        rvProductList.hide()
                    }
                    is UiState.Success -> {
                        if(productList.data.isEmpty()){
                            pbProductPageProgress.hide()
                            tvProductListPageMessage.show()
                            rvProductList.hide()
                            tvProductListPageMessage.text = "Products out of Stock for this category"
                        }else {
                            pbProductPageProgress.hide()
                            tvProductListPageMessage.hide()
                            rvProductList.show()
                            productListAdapter.submitList(productList.data)
                        }
                    }
                    is UiState.Error -> {
                        pbProductPageProgress.hide()
                        tvProductListPageMessage.show()
                        rvProductList.hide()
                        tvProductListPageMessage.text = productList.message
                    }
                }
            }
        }
    }

    companion object{
        const val ARG_SUBCATEGORY_ID = "subCategoryId"
    }
}