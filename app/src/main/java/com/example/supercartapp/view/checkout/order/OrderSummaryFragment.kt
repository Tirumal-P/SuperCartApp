package com.example.supercartapp.view.checkout.order

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentOrderSummaryBinding
import com.example.supercartapp.model.local.SuperCartDatabase
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.model.PaymentType
import com.example.supercartapp.model.local.relation.CartWithCartItems
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.repository.CartRepositoryImpl
import com.example.supercartapp.repository.DeliveryRepositoryImpl
import com.example.supercartapp.repository.OrderRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.view.checkout.finalcart.FinalCartAdapter
import com.example.supercartapp.viewmodel.CartViewModel
import com.example.supercartapp.viewmodel.CheckoutViewModel

class OrderSummaryFragment : Fragment(R.layout.fragment_order_summary) {

    private lateinit var binding: FragmentOrderSummaryBinding
    private lateinit var cartAdapter: FinalCartAdapter

    private var currentCart: CartWithCartItems? = null
    private var totalAmount = 0

    private val cartViewModel: CartViewModel by viewModels {
        val repository = CartRepositoryImpl(
            SuperCartDatabase.getDatabase(requireContext()).cartDao()
        )
        CartViewModel.CartViewModelFactory(repository)
    }

    private val checkoutViewModel: CheckoutViewModel by navGraphViewModels(R.id.nav_checkout) {
        val deliveryRepository = DeliveryRepositoryImpl(ApiClient.apiService)
        val orderRepository = OrderRepositoryImpl(ApiClient.apiService)
        CheckoutViewModel.CheckoutViewModelFactory(deliveryRepository, orderRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderSummaryBinding.bind(view)
        setUpView()
        setUpObservers()
        setUpEventHandling()
    }

    private fun setUpView() {
        cartAdapter = FinalCartAdapter()

        binding.rvCartItemList.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.header.tvSummaryStep.setTextColor(
            ContextCompat.getColor(requireContext(), R.color.primary)
        )
    }

    private fun setUpObservers() {

        cartViewModel.cartWithItems.observe(viewLifecycleOwner){ cart ->
            currentCart = cart

            cart?.let {
                setCartContent(it.cartItems)
            }
        }

        checkoutViewModel.selectedAddress.observe(viewLifecycleOwner){ address ->
            with(binding){
                tvAddressTitle.text = address.title
                tvDeliveryAddress.text = address.address
            }
        }

        checkoutViewModel.selectedPaymentType.observe(viewLifecycleOwner){ paymentType ->
            binding.tvPaymentOption.text = getPaymentText(paymentType)
        }

        checkoutViewModel.placeOrderState.observe(viewLifecycleOwner){ state ->
            when(state){

                is UiState.Loading -> {
                    binding.btnConfirmOrder.isEnabled = false
                }

                is UiState.Success -> {
                    binding.btnConfirmOrder.isEnabled = true

                    val cart = currentCart ?: return@observe

                    cartViewModel.makeCartInactive(
                        cart.cartEntity.cartId
                    )

                    findNavController().navigate(
                        R.id.action_orderSummaryFragment_to_orderConfirmationFragment
                    )
                }

                is UiState.Error -> {
                    binding.btnConfirmOrder.isEnabled = true

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setCartContent(cartItems: List<CartItemEntity>) {
        cartAdapter.submitList(cartItems)

        totalAmount = cartItems.sumOf {
            it.productPrice * it.productQuantity
        }

        binding.tvCartTotalAmount.text = "\$$totalAmount"
    }

    private fun getPaymentText(paymentType: PaymentType?): String {
        return when(paymentType){
            PaymentType.CASH_ON_DELIVERY -> "COD"
            PaymentType.INTERNET_BANKING -> "Internet Banking"
            PaymentType.CARD -> "Debit Card / Credit Card"
            PaymentType.PAYPAL -> "Pay Pal"
            null -> ""
        }
    }

    private fun setUpEventHandling() {
        binding.btnConfirmOrder.setOnClickListener {

            val cart = currentCart ?: return@setOnClickListener

            checkoutViewModel.setConfirmationCartItems(
                cart.cartItems
            )

            checkoutViewModel.placeOrder(
                cartItems = cart.cartItems,
                total = totalAmount
            )
        }
    }
}