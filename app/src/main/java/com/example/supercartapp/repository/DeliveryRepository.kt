package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.request.AddAddressRequest
import com.example.supercartapp.model.remote.response.AddAddressResponse
import com.example.supercartapp.model.remote.response.AddressResponse

interface DeliveryRepository {

    suspend fun getUserAddresses(userId: Int): AddressResponse

    suspend fun addUserAddress(addAddressRequest: AddAddressRequest): AddAddressResponse
}