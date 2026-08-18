package com.example.supercartapp.view.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.OrderDetailsItemBinding
import com.example.supercartapp.model.remote.response.Item
import com.example.supercartapp.util.GenericDiffUtil

class OrderDetailsAdapter :
    ListAdapter<Item, OrderDetailsViewHolder>(
        GenericDiffUtil({ it.productId })
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailsViewHolder {
        val binding = OrderDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OrderDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderDetailsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}