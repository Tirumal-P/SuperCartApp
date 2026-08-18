package com.example.supercartapp.view.productdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentProductDetailsBinding
import com.example.supercartapp.model.local.SuperCartDatabase
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.model.CartProduct
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.model.remote.response.Image
import com.example.supercartapp.model.remote.response.Product
import com.example.supercartapp.repository.CartRepositoryImpl
import com.example.supercartapp.repository.ProductRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.CartViewModel
import com.example.supercartapp.viewmodel.ProductDetailsViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    private lateinit var binding: FragmentProductDetailsBinding
    private val productDetailsViewModel : ProductDetailsViewModel by viewModels {
        val repository = ProductRepositoryImpl(ApiClient.apiService)
        ProductDetailsViewModel.ProductDetailsViewModelFactory(repository)
    }

    private val cartViewModel: CartViewModel by viewModels {
        val repository = CartRepositoryImpl(SuperCartDatabase.getDatabase(requireContext()).cartDao())
        CartViewModel.CartViewModelFactory(repository)
    }

    private lateinit var productDetailsRatingAdapter: ProductDetailsRatingAdapter

    private lateinit var productDetailsSpecificationAdapter: ProductDetailsSpecificationAdapter

    private val args: ProductDetailsFragmentArgs by navArgs()

    private var currentCartItem: CartItemEntity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailsBinding.bind(view)
        setUpObserver()
        setUpRecyclerViews()
        setUpEventHandling()
        val productId = args.productId
        productDetailsViewModel.getProductDetails(productId)
    }

    private fun setUpEventHandling() {
        with(binding){
            acbProductAddToCart.setOnClickListener {
                val state = productDetailsViewModel.productDetails.value
                if(state is UiState.Success){
                    val product = state.data
                    val cartProduct = CartProduct(
                        productId = product.productId.toLong(),
                        productName = product.productName,
                        description = product.description,
                        price = product.price.toInt(),
                        imageUrl = product.images[0].image
                    )
                    cartViewModel.addToCart(cartProduct)
                }
            }

            imgBtnDecreaseProductQuantity.setOnClickListener {
                currentCartItem?.let {
                    cartViewModel.cartItemDecreaseQuantity(it)
                }
            }

            imgBtnIncreaseProductQuantity.setOnClickListener {
                currentCartItem?.let {
                    cartViewModel.cartItemIncreaseQuantity(it)
                }
            }
        }
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

            cartViewModel.cartWithItems.observe(viewLifecycleOwner){cartWithCartItems ->
                with(binding){
                    if (cartWithCartItems == null) {
                        acbProductAddToCart.hideRest(productQuantitySelector)
                        return@observe
                    }
                    val cartItem = cartWithCartItems.cartItems.find {
                        it.productId == args.productId.toLong()
                    }
                    currentCartItem = cartItem
                    if(cartItem !=null && cartItem.productQuantity >0) {
                        tvProductQuantity.text = cartItem.productQuantity.toString()
                        productQuantitySelector.hideRest(acbProductAddToCart)
                    }else{
                        acbProductAddToCart.hideRest(productQuantitySelector)
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
            tvProductPrice.text = "\$${product.price}"
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