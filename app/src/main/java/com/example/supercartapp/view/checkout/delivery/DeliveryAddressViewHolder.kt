package com.example.supercartapp.view.checkout.delivery

import androidx.recyclerview.widget.RecyclerView
import com.example.supercartapp.R
import com.example.supercartapp.databinding.DeliveryItemBinding
import com.example.supercartapp.model.local.model.AddressUiItem
import com.example.supercartapp.model.remote.response.Address

class DeliveryAddressViewHolder(
    private val binding: DeliveryItemBinding,
    private val onAddressClick: (Address) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AddressUiItem) {

        with(binding) {

            val address = item.address

            tvAddressTitle.text = address.title
            tvAddress.text = address.address

            if (item.isSelected) {
                imgvAddressSelected.setImageResource(
                    R.drawable.checked_circle_icon
                )
            } else {
                imgvAddressSelected.setImageResource(
                    R.drawable.circle_icon
                )
            }

            root.setOnClickListener {
                onAddressClick(address)
            }

            imgvAddressSelected.setOnClickListener {
                onAddressClick(address)
            }
        }
    }
}