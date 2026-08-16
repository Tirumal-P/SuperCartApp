package com.example.supercartapp.view.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentCartBinding
import com.example.supercartapp.model.local.SuperCartDatabase
import com.example.supercartapp.model.local.relation.CartWithCartItems
import com.example.supercartapp.repository.CartRepositoryImpl
import com.example.supercartapp.viewmodel.CartViewModel

class CartFragment : Fragment(R.layout.fragment_cart) {

    lateinit var binding: FragmentCartBinding
    lateinit var cartAdapter: CartAdapter
    private val cartViewModel: CartViewModel by viewModels {
        val repository = CartRepositoryImpl(SuperCartDatabase.getDatabase(requireContext()).cartDao())
        CartViewModel.CartViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        setUpObservers()
    }

    private fun setUpObservers() {
        with(binding){
//            cartViewModel.cartState.observe(viewLifecycleOwner){
//                when(it){
//                    is UiState.Loading -> pbCartProgress.hideRest(rvCartItemList,tvCartMessage)
//
//                    is UiState.Success -> {
//                        setContent(it.data)
//                        rvCartItemList.hideRest(tvCartMessage,pbCartProgress)
//                    }
//
//                    is UiState.Error -> {
//                        tvCartMessage.text = it.message
//                        tvCartMessage.hideRest(rvCartItemList,pbCartProgress)
//                    }
//                }
//            }
        }
    }

    private fun setContent(cartWithCartItems: CartWithCartItems) {
        cartAdapter.submitList(cartWithCartItems.cartItems)
        var totalAmount = 0
        cartWithCartItems.cartItems.forEach {
            totalAmount += it.productPrice
        }
    }
}