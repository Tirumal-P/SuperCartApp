package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.request.PlaceOrderRequest
import com.example.supercartapp.model.remote.response.PlaceOrderResponse

interface OrderRepository {

    suspend fun placeOrder(orderRequest: PlaceOrderRequest): PlaceOrderResponse
}