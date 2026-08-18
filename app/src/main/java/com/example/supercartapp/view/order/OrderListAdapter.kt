package com.example.supercartapp.view.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.OrderListItemBinding
import com.example.supercartapp.model.remote.response.Order
import com.example.supercartapp.util.GenericDiffUtil

class OrderListAdapter(
    private val onOrderClick: (Order) -> Unit
) : ListAdapter<Order, OrderListViewHolder>(
    GenericDiffUtil({ it.orderId })
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderListViewHolder {
        val binding = OrderListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderListViewHolder,
        position: Int
    ) {
        holder.binding.root.setOnClickListener {
            onOrderClick(getItem(position))
        }
        holder.bind(getItem(position))
    }
}