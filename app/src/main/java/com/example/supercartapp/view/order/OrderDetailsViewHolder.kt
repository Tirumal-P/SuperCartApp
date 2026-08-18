package com.example.supercartapp.view.order

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.databinding.OrderDetailsItemBinding
import com.example.supercartapp.model.remote.response.Item
import com.example.supercartapp.util.ImageGlide

class OrderDetailsViewHolder(
    private val binding: OrderDetailsItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(orderItem: Item) {
        with(binding){
            tvProductName.text = orderItem.productName
            tvProductDescription.text = orderItem.description
            tvQuantity.text = "Qty: ${orderItem.quantity}"
            tvAmount.text = "$${orderItem.amount}"

            ImageGlide.glide(
                imgvProductImage,
                orderItem.productImageUrl
            )
        }
    }
}