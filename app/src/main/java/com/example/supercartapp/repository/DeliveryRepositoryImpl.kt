package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.ApiService
import com.example.supercartapp.model.remote.request.AddAddressRequest
import com.example.supercartapp.model.remote.response.AddAddressResponse
import com.example.supercartapp.model.remote.response.AddressResponse

class DeliveryRepositoryImpl(val apiService: ApiService): DeliveryRepository {
    override suspend fun getUserAddresses(userId: Int): AddressResponse {
        return apiService.getUserAddresses(userId)
    }

    override suspend fun addUserAddress(addAddressRequest: AddAddressRequest): AddAddressResponse {
        return apiService.addUserAddress(addAddressRequest)
    }
}