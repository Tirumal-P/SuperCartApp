package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.local.preferences.LoginPreferences
import com.example.supercartapp.model.remote.response.Order
import com.example.supercartapp.model.remote.response.OrderX
import com.example.supercartapp.repository.OrderRepository
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    private val _orderListState = MutableLiveData<UiState<List<Order>>>()
    val orderListState: LiveData<UiState<List<Order>>>
        get() = _orderListState

    private val _orderDetailsState = MutableLiveData<UiState<OrderX>>()
    val orderDetailsState: LiveData<UiState<OrderX>>
        get() = _orderDetailsState

    private val userId = LoginPreferences.getUserId()

    fun getOrders(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _orderListState.postValue(UiState.Loading)

                val response = repository.getOrderListByUserId(userId)

                if(response.status == 0){
                    _orderListState.postValue(
                        UiState.Success(response.orders)
                    )
                }else{
                    _orderListState.postValue(
                        UiState.Error(response.message)
                    )
                }

            }catch (e: Exception){
                _orderListState.postValue(
                    UiState.Error(e.message ?: "Something went wrong")
                )
            }
        }
    }

    fun getOrderDetails(orderId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _orderDetailsState.postValue(UiState.Loading)

                val response = repository.getOrderDetails(orderId)

                if(response.status == 0 && response.order != null){
                    _orderDetailsState.postValue(
                        UiState.Success(response.order)
                    )
                }else{
                    _orderDetailsState.postValue(
                        UiState.Error(response.message)
                    )
                }

            }catch (e: Exception){
                _orderDetailsState.postValue(
                    UiState.Error(e.message ?: "Something went wrong")
                )
            }
        }
    }

    class OrderViewModelFactory(val repository: OrderRepository): ViewModelProvider.NewInstanceFactory(){

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(OrderViewModel::class.java)){
                return OrderViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel for type OrderViewModel")
        }
    }
}