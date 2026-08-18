package com.example.supercartapp.view.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentOrderDetailsBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.model.remote.response.OrderX
import com.example.supercartapp.repository.OrderRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.OrderViewModel

class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter

    private val args: OrderDetailsFragmentArgs by navArgs()

    private val orderViewModel: OrderViewModel by viewModels {
        val repository = OrderRepositoryImpl(ApiClient.apiService)
        OrderViewModel.OrderViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderDetailsBinding.bind(view)
        setUpView()
        setUpObservers()
        orderViewModel.getOrderDetails(args.orderId)
    }

    private fun setUpView() {
        orderDetailsAdapter = OrderDetailsAdapter()

        binding.rvOrderItemList.apply {
            adapter = orderDetailsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservers() {
        orderViewModel.orderDetailsState.observe(viewLifecycleOwner){ state ->
            with(binding){
                when(state){
                    is UiState.Loading -> {
                        pbOrderDetailsProgress.hideRest(tvOrderMessage, rvOrderItemList)
                    }

                    is UiState.Error -> {
                        tvOrderMessage.text = state.message
                        tvOrderMessage.hideRest(rvOrderItemList, pbOrderDetailsProgress)
                    }

                    is UiState.Success -> {
                        setContent(state.data)
                        rvOrderItemList.hideRest(tvOrderMessage, pbOrderDetailsProgress)
                    }
                }
            }
        }
    }

    private fun setContent(order: OrderX) {
        with(binding){
            tvOrderId.text = "#${order.orderId}"
            tvOrderStatus.text = order.orderStatus
            tvOrderDate.text = order.orderDate
            tvTotalAmount.text = "$${order.billAmount}"
            tvAddressTitle.text = order.addressTitle
            tvAddress.text = order.address
            tvPaymentMethod.text = order.paymentMethod

            orderDetailsAdapter.submitList(order.items)
        }
    }
}