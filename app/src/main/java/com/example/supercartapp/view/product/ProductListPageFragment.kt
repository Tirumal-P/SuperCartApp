package com.example.supercartapp.view.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentProductListPageBinding
import com.example.supercartapp.model.local.SuperCartDatabase
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.model.CartProduct
import com.example.supercartapp.model.local.model.ProductListUiItem
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.model.remote.response.ProductItem
import com.example.supercartapp.repository.CartRepositoryImpl
import com.example.supercartapp.repository.ProductRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hide
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.util.show
import com.example.supercartapp.viewmodel.CartViewModel
import com.example.supercartapp.viewmodel.ProductListViewModel

class ProductListPageFragment : Fragment(R.layout.fragment_product_list_page) {

    private lateinit var binding: FragmentProductListPageBinding
    private val productListViewModel: ProductListViewModel by viewModels {
        val repository = ProductRepositoryImpl(ApiClient.apiService)
        ProductListViewModel.ProductListViewModelFactory(repository)
    }

    private lateinit var productListAdapter: ProductListAdapter

    private val cartViewModel: CartViewModel by viewModels {
        val database = SuperCartDatabase.getDatabase(requireContext())
        val cartRepository = CartRepositoryImpl(database.cartDao())
        CartViewModel.CartViewModelFactory(cartRepository)
    }

    private var currentProducts: List<ProductItem> = emptyList()
    private var currentCartItems: List<CartItemEntity> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProductListPageBinding.bind(view)
        setUpView()
    }

    private fun setUpView() {
        with(binding) {
            val subCategoryId = arguments?.getInt(ARG_SUBCATEGORY_ID)
            if (subCategoryId != null) {
                productListAdapter = ProductListAdapter (
                    {product -> onProductClick(product)},
                    {product-> onAddToCart(product)},
                    {cartItem-> onIncreaseQuantity(cartItem)},
                    {cartItem-> onDecreaseQuantity(cartItem)}
                )

                rvProductList.apply {
                    adapter = productListAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }

                setUpObserver()

                productListViewModel.getProducts(subCategoryId)
            } else {
                tvProductListPageMessage.show()
                pbProductPageProgress.hide()
                rvProductList.hide()
                tvProductListPageMessage.text = "Error in Fetching Subcategory Id"
            }
        }
    }

    private fun updateProductUiList() {

        val uiList = currentProducts.map { product ->

            val cartItem = currentCartItems.find {
                it.productId == product.productId.toLong()
            }

            ProductListUiItem(
                product = product,
                cartItem = cartItem
            )
        }

        productListAdapter.submitList(uiList)
    }
    fun setUpObserver() {
        with(binding) {
            productListViewModel.products.observe(viewLifecycleOwner) { productList ->
                when (productList) {
                    is UiState.Loading -> {
                        pbProductPageProgress.hideRest(tvProductListPageMessage, rvProductList)
                    }

                    is UiState.Success -> {
                        if (productList.data.isEmpty()) {
                            currentProducts = emptyList()
                            updateProductUiList()
                            tvProductListPageMessage.hideRest(rvProductList, pbProductPageProgress)
                            tvProductListPageMessage.text =
                                "Products out of Stock for this category"
                        } else {
                            rvProductList.hideRest(pbProductPageProgress, tvProductListPageMessage)
                            currentProducts = productList.data
                            updateProductUiList()
                        }
                    }

                    is UiState.Error -> {
                        tvProductListPageMessage.hideRest(pbProductPageProgress, rvProductList)
                        tvProductListPageMessage.text = productList.message
                    }
                }
            }

            cartViewModel.cartWithItems.observe(viewLifecycleOwner) { cart ->
                currentCartItems =
                    cart?.cartItems ?: emptyList()
                updateProductUiList()
            }
        }
    }

    private fun onProductClick(productItem: ProductItem) {
        val action =
            ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(
                productId = productItem.productId.toInt()
            )
        findNavController().navigate(action)
    }

    private fun onAddToCart(productItem: ProductItem) {
        val cartProduct = CartProduct(
            productId = productItem.productId.toLong(),
            productName = productItem.productName,
            description = productItem.description,
            price = productItem.price.toInt(),
            imageUrl = productItem.productImageUrl
        )
        cartViewModel.addToCart(cartProduct)
    }

    private fun onIncreaseQuantity(cartItemEntity: CartItemEntity) {
        cartViewModel.cartItemIncreaseQuantity(cartItemEntity)
    }

    private fun onDecreaseQuantity(cartItemEntity: CartItemEntity) {
        cartViewModel.cartItemDecreaseQuantity(cartItemEntity)
    }

    companion object {
        const val ARG_SUBCATEGORY_ID = "subCategoryId"
    }
}