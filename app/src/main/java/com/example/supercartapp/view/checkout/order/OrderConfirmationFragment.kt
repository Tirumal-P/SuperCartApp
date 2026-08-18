package com.example.supercartapp.view.checkout.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentOrderConfirmationBinding
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.model.PaymentType
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.repository.DeliveryRepositoryImpl
import com.example.supercartapp.repository.OrderRepositoryImpl
import com.example.supercartapp.view.checkout.finalcart.FinalCartAdapter
import com.example.supercartapp.viewmodel.CheckoutViewModel

class OrderConfirmationFragment : Fragment(R.layout.fragment_order_confirmation) {

    private lateinit var binding: FragmentOrderConfirmationBinding
    private lateinit var cartAdapter: FinalCartAdapter

    private val checkoutViewModel: CheckoutViewModel by navGraphViewModels(R.id.nav_checkout) {
        val deliveryRepository = DeliveryRepositoryImpl(ApiClient.apiService)
        val orderRepository = OrderRepositoryImpl(ApiClient.apiService)
        CheckoutViewModel.CheckoutViewModelFactory(deliveryRepository, orderRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderConfirmationBinding.bind(view)
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        cartAdapter = FinalCartAdapter()

        binding.rvCartItemList.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservers() {

        checkoutViewModel.orderId.observe(viewLifecycleOwner){ orderId ->
            binding.tvOrderId.text = "#${orderId.toString()}"
        }

        checkoutViewModel.confirmedCartItems.observe(viewLifecycleOwner){ cartItems ->
            setCartContent(cartItems)
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
    }

    private fun setCartContent(cartItems: List<CartItemEntity>) {
        cartAdapter.submitList(cartItems)

        val totalAmount = cartItems.sumOf {
            it.productPrice * it.productQuantity
        }

        binding.tvCartTotalAmount.text = "$$totalAmount"
        binding.tvOrderStatus.text = "Order Placed"
    }

    private fun getPaymentText(paymentType: PaymentType?): String {
        return when(paymentType){
            PaymentType.CASH_ON_DELIVERY -> "Cash On Delivery"
            PaymentType.INTERNET_BANKING -> "Internet Banking"
            PaymentType.CARD -> "Debit Card & Credit Card"
            PaymentType.PAYPAL -> "Pay Pal"
            null -> ""
        }
    }
}