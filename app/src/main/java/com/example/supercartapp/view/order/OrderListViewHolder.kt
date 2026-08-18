package com.example.supercartapp.view.order

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.OrderListItemBinding
import com.example.supercartapp.model.remote.response.Order

class OrderListViewHolder(
    val binding: OrderListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order){
        with(binding){
            tvOrderId.text = "Order #${order.orderId}"
            tvOrderStatus.text = order.orderStatus
            tvAddressTitle.text = order.addressTitle
            tvAddress.text = order.address
            tvPaymentMethod.text = order.paymentMethod
            tvOrderAmount.text = "$${order.billAmount}"
            tvOrderDate.text = order.orderDate
        }
    }
}