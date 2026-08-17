package com.example.supercartapp.view.checkout.delivery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.supercartapp.databinding.DeliveryItemBinding
import com.example.supercartapp.model.local.model.AddressUiItem
import com.example.supercartapp.model.remote.response.Address
import com.example.supercartapp.util.GenericDiffUtil

class DeliveryAddressAdapter(
    private val onAddressClick: (Address) -> Unit
) : ListAdapter<AddressUiItem, DeliveryAddressViewHolder>(
    GenericDiffUtil({ it.address.addressId })
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryAddressViewHolder {

        val binding = DeliveryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DeliveryAddressViewHolder(
            binding,
            onAddressClick
        )
    }

    override fun onBindViewHolder(
        holder: DeliveryAddressViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}