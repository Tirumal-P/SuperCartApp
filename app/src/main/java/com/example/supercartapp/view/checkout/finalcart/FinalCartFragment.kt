package com.example.supercartapp.view.checkout.finalcart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.databinding.FragmentFinalCartBinding
import com.example.supercartapp.model.local.SuperCartDatabase
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.repository.CartRepositoryImpl
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.CartViewModel

class FinalCartFragment : Fragment() {

    private lateinit var binding: FragmentFinalCartBinding

    private val cartViewModel: CartViewModel by viewModels {
        val database = SuperCartDatabase.getDatabase(requireContext())
        CartViewModel.CartViewModelFactory(CartRepositoryImpl(database.cartDao()))
    }

    private lateinit var finalCartAdapter: FinalCartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFinalCartBinding.bind(view)
        setUpView()
    }

    private fun setUpView() {
        finalCartAdapter = FinalCartAdapter()
        binding.rvCartItemList.apply {
            adapter = finalCartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservers() {
        with(binding) {
            cartViewModel.cartWithItems.observe(viewLifecycleOwner) { cart ->
                if (cart == null || cart.cartItems.isEmpty()) {
                    tvFinalCartMessage.text = "Your Current Cart is Empty"
                    tvFinalCartMessage.hideRest(rvCartItemList)
                } else {
                    rvCartItemList.hideRest(tvFinalCartMessage)
                    setContent(cart.cartItems)
                }
            }
        }
    }

    private fun setContent(cartItems: List<CartItemEntity>) {
        finalCartAdapter.submitList(cartItems)
        var totalAmount = 0
        cartItems.forEach {
            totalAmount += (it.productPrice * it.productQuantity)
        }
        binding.tvTotal.text = "$$totalAmount"
    }


}