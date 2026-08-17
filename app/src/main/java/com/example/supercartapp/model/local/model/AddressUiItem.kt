package com.example.supercartapp.model.local.model

import com.example.supercartapp.model.remote.response.Address

data class AddressUiItem(
    val address: Address,
    val isSelected: Boolean
)