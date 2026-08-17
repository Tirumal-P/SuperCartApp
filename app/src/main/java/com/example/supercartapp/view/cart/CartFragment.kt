package com.example.supercartapp.view.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentCartBinding
import com.example.supercartapp.model.local.SuperCartDatabase
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.repository.CartRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hide
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.CartViewModel

class CartFragment : Fragment(R.layout.fragment_cart) {

    lateinit var binding: FragmentCartBinding
    lateinit var cartAdapter: CartAdapter
    private val cartViewModel: CartViewModel by viewModels {
        val repository =
            CartRepositoryImpl(SuperCartDatabase.getDatabase(requireContext()).cartDao())
        CartViewModel.CartViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        setUpCartItemList()
        setUpObservers()
    }

    private fun setUpCartItemList() {
        cartAdapter = CartAdapter(
            { cartItem -> onIncreaseQuantity(cartItem) },
            { cartItem -> onDecreaseQuantity(cartItem) }
        )
        binding.rvCartItemList.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservers() {
        with(binding) {
            cartViewModel.cartState.observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> pbCartProgress.hideRest(rvCartItemList, tvCartMessage)

                    is UiState.Success -> {
                        pbCartProgress.hide()
                    }

                    is UiState.Error -> {
                        tvCartMessage.text = it.message
                        tvCartMessage.hideRest(rvCartItemList, pbCartProgress)
                    }
                }
            }
            cartViewModel.cartWithItems.observe(viewLifecycleOwner) { cart ->
                if (cart == null || cart.cartItems.isEmpty()) {
                    tvCartMessage.text = "Your Current Cart is Empty"
                    tvCartMessage.hideRest(rvCartItemList, pbCartProgress)
                } else {
                    rvCartItemList.hideRest(tvCartMessage, pbCartProgress)
                    setContent(cart.cartItems)
                }
            }
        }
    }

    private fun setContent(cartItems: List<CartItemEntity>) {
        cartAdapter.submitList(cartItems)
        var totalAmount = 0
        cartItems.forEach {
            totalAmount += (it.productPrice * it.productQuantity)
        }
        binding.tvCartTotalText.text = "$$totalAmount"
    }

    private fun onIncreaseQuantity(cartItem: CartItemEntity) {
        cartViewModel.cartItemIncreaseQuantity(cartItem)
    }

    private fun onDecreaseQuantity(cartItem: CartItemEntity) {
        cartViewModel.cartItemDecreaseQuantity(cartItem)
    }
}