package com.example.supercartapp.view.productdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentProductDetailsBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.model.response.Image
import com.example.supercartapp.model.response.Product
import com.example.supercartapp.repository.SuperCartRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.ProductDetailsViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    private lateinit var binding: FragmentProductDetailsBinding
    private val productDetailsViewModel : ProductDetailsViewModel by viewModels {
        val repository = SuperCartRepositoryImpl(ApiClient.apiService)
        ProductDetailsViewModel.ProductDetailsViewModelFactory(repository)
    }

    private lateinit var productDetailsRatingAdapter: ProductDetailsRatingAdapter

    private lateinit var productDetailsSpecificationAdapter: ProductDetailsSpecificationAdapter

    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)
        setUpObserver()
        setUpRecyclerViews()
        val productId = args.productId
        productDetailsViewModel.getProductDetails(productId)
    }

    private fun setUpRecyclerViews() {
        with(binding){
            productDetailsSpecificationAdapter = ProductDetailsSpecificationAdapter()
            rvSpecificationsList.apply {
                adapter = productDetailsSpecificationAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            productDetailsRatingAdapter = ProductDetailsRatingAdapter()
            rvReviewsList.apply {
                adapter = productDetailsRatingAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setUpObserver() {
        with(binding){
            productDetailsViewModel.productDetails.observe(viewLifecycleOwner){
                when(it){
                    is UiState.Loading -> pbProductDetailsProgress.hideRest(clProductDetails,tvProductDetailsMessage)
                    is UiState.Success -> {
                        setUpUiData(it.data)
                        clProductDetails.hideRest(pbProductDetailsProgress, tvProductDetailsMessage)
                    }
                    is UiState.Error -> {
                        tvProductDetailsMessage.text = it.message
                        tvProductDetailsMessage.hideRest(pbProductDetailsProgress, clProductDetails)
                    }
                }
            }
        }
    }

    fun setUpUiData(product: Product) {
        with(binding){
            tvProductName.text = product.productName
            rbProductRating.rating = product.averageRating.toFloat()
            tvProductDescription.text = product.description
            tvProductPrice.text = product.price
            if(product.specifications.isEmpty()) {
                tvEmptySpecification.hideRest(rvSpecificationsList)
            }
            else {
                productDetailsSpecificationAdapter.submitList(product.specifications)
                rvSpecificationsList.hideRest(tvEmptySpecification)
            }

            if(product.reviews.isEmpty()){
                tvEmptyReview.hideRest(rvReviewsList)
            }else{
                productDetailsRatingAdapter.submitList(product.reviews)
                rvReviewsList.hideRest(tvEmptyReview)
            }
            setUpCarousel(product.images)
        }
    }

    fun setUpCarousel(imageList:List<Image>) {

        with(binding){
            vpProductImages.adapter = ProductDetailsImageViewPager(this@ProductDetailsFragment, imageList)
            TabLayoutMediator(tbImageCarousel, vpProductImages){tab, _->
                val customView = layoutInflater.inflate(R.layout.product_details_custom_tab_layout, tbImageCarousel, false)
                tab.customView = customView
            }.attach()
        }
    }

}