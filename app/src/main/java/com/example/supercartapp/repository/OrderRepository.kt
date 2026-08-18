package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.request.PlaceOrderRequest
import com.example.supercartapp.model.remote.response.OrderDetailsResponse
import com.example.supercartapp.model.remote.response.OrderListResponse
import com.example.supercartapp.model.remote.response.PlaceOrderResponse

interface OrderRepository {

    suspend fun placeOrder(orderRequest: PlaceOrderRequest): PlaceOrderResponse

    suspend fun getOrderListByUserId(userId: Int): OrderListResponse

    suspend fun getOrderDetails(orderId: Int): OrderDetailsResponse
}