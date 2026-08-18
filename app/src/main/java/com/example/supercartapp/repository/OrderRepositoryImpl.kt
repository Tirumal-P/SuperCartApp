package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.ApiService
import com.example.supercartapp.model.remote.request.PlaceOrderRequest
import com.example.supercartapp.model.remote.response.OrderListResponse
import com.example.supercartapp.model.remote.response.PlaceOrderResponse

class OrderRepositoryImpl(val apiService: ApiService): OrderRepository {
    override suspend fun placeOrder(orderRequest: PlaceOrderRequest): PlaceOrderResponse {
        return apiService.placeOrder(orderRequest)
    }

    override suspend fun getOrderListByUserId(userId: Int): OrderListResponse {
        return apiService.getOrderListByUserId(userId)
    }
}