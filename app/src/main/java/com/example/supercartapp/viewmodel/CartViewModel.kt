package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.local.relation.CartWithCartItems
import com.example.supercartapp.repository.CartRepository
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(val repository: CartRepository): ViewModel() {

    private val _cartState = MutableLiveData<UiState<CartWithCartItems>>()
    val cartState: LiveData<UiState<CartWithCartItems>>
        get() = _cartState

    fun getCart(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cartState.postValue(UiState.Loading)
                val response = repository.getCartWithCartItemsByUserId(1)
                if(response != null){
                    _cartState.postValue(UiState.Success(response))
                }else{
                    _cartState.postValue(UiState.Error("Your Cart is Currently Empty"))
                }
            }catch (e: Exception){
                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
            }
        }
    }

    class CartViewModelFactory(val repository: CartRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
                return CartViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel for type HomeViewModel")
        }
    }
}