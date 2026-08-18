package com.example.supercartapp.view.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supercartapp.R
import com.example.supercartapp.databinding.FragmentOrderListBinding
import com.example.supercartapp.model.remote.ApiClient
import com.example.supercartapp.model.remote.response.Order
import com.example.supercartapp.repository.OrderRepositoryImpl
import com.example.supercartapp.util.UiState
import com.example.supercartapp.util.hideRest
import com.example.supercartapp.viewmodel.OrderViewModel

class OrderListFragment : Fragment(R.layout.fragment_order_list) {

    private lateinit var binding: FragmentOrderListBinding
    private lateinit var orderListAdapter: OrderListAdapter

    private val orderViewModel: OrderViewModel by viewModels {
        val repository = OrderRepositoryImpl(ApiClient.apiService)
        OrderViewModel.OrderViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderListBinding.bind(view)
        setUpView()
        setUpObservers()
        orderViewModel.getOrders()
    }

    private fun setUpView() {
        orderListAdapter = OrderListAdapter{ order ->
            onOrderClick(order)
        }

        binding.rvOrderList.apply {
            adapter = orderListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservers() {
        orderViewModel.orderListState.observe(viewLifecycleOwner){ state ->
            with(binding){
                when(state){
                    is UiState.Loading -> {
                        pbOrderProgress.hideRest(rvOrderList, tvOrderMessage)
                    }

                    is UiState.Error -> {
                        tvOrderMessage.text = state.message
                        tvOrderMessage.hideRest(rvOrderList, pbOrderProgress)
                    }

                    is UiState.Success -> {
                        if(state.data.isEmpty()){
                            tvOrderMessage.text = "No orders found"
                            tvOrderMessage.hideRest(rvOrderList, pbOrderProgress)
                        }else{
                            orderListAdapter.submitList(state.data)
                            rvOrderList.hideRest(tvOrderMessage, pbOrderProgress)
                        }
                    }
                }
            }
        }
    }

    private fun onOrderClick(order: Order){
    }
}