package com.example.supercartapp.view.checkout.payment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentPaymentBinding
import com.example.supercartapp.model.local.model.PaymentType
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.repository.DeliveryRepositoryImpl
import com.example.supercartapp.repository.OrderRepositoryImpl
import com.example.supercartapp.viewmodel.CheckoutViewModel
import kotlin.getValue

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding

    private val checkoutViewModel: CheckoutViewModel by navGraphViewModels(R.id.nav_checkout) {
        val deliveryRepository = DeliveryRepositoryImpl(ApiClient.apiService)
        val orderRepository = OrderRepositoryImpl(ApiClient.apiService)
        CheckoutViewModel.CheckoutViewModelFactory(deliveryRepository, orderRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)
        setUpEventHandling()
        setUpObservers()
        binding.header.tvPaymentStep.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary))
    }

    private fun setUpEventHandling() {
        with(binding){
            clCashOnDelivery.setOnClickListener {
                checkoutViewModel.onSelectPaymentType(PaymentType.CASH_ON_DELIVERY)
            }

            clInternetBanking.setOnClickListener {
                checkoutViewModel.onSelectPaymentType(PaymentType.INTERNET_BANKING)
            }

            clCard.setOnClickListener {
                checkoutViewModel.onSelectPaymentType(PaymentType.CARD)
            }

            clPaypal.setOnClickListener {
                checkoutViewModel.onSelectPaymentType(PaymentType.PAYPAL)
            }

            btnPaymentNext.setOnClickListener {
                if(checkoutViewModel.selectedPaymentType.value == null){
                    Toast.makeText(requireContext(),"Please select a payment method",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                findNavController().navigate(R.id.action_paymentFragment_to_orderSummaryFragment)
            }
        }
    }

    private fun setUpObservers() {
        checkoutViewModel.selectedPaymentType.observe(viewLifecycleOwner){ paymentType ->
            updatePaymentUi(paymentType)
        }
    }

    private fun updatePaymentUi(paymentType: PaymentType?) {
        with(binding){
            imgCashOnDelivery.setImageResource(
                if(paymentType == PaymentType.CASH_ON_DELIVERY)
                    R.drawable.checked_circle_icon
                else
                    R.drawable.circle_icon
            )

            imgInternetBanking.setImageResource(
                if(paymentType == PaymentType.INTERNET_BANKING)
                    R.drawable.checked_circle_icon
                else
                    R.drawable.circle_icon
            )

            imgCard.setImageResource(
                if(paymentType == PaymentType.CARD)
                    R.drawable.checked_circle_icon
                else
                    R.drawable.circle_icon
            )

            imgPaypal.setImageResource(
                if(paymentType == PaymentType.PAYPAL)
                    R.drawable.checked_circle_icon
                else
                    R.drawable.circle_icon
            )
        }
    }
}