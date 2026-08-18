package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.local.entity.CartItemEntity
import com.example.supercartapp.model.local.model.CartProduct
import com.example.supercartapp.model.local.preferences.LoginPreferences
import com.example.supercartapp.repository.CartRepository
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(val repository: CartRepository): ViewModel() {

    private val _cartState = MutableLiveData<UiState<Long>>()
    val cartState: LiveData<UiState<Long>>
        get() = _cartState

    private val userId: Long =
        LoginPreferences.getUserId().toLong()

    val cartWithItems = repository.getCartWithCartItemsByUserId(userId)

    fun cartItemIncreaseQuantity(cartItem: CartItemEntity){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedCartItem = cartItem.copy(
                    productQuantity = cartItem.productQuantity+1
                )
                val response = repository.updateCartItem(updatedCartItem)
                if(response !=0) {
                    _cartState.postValue(UiState.Success(response.toLong()))
                }else{
                    _cartState.postValue(UiState.Error("Couldn't able to find Cart Record with Id= ${cartItem.cartItemId}"))
                }
            }catch (e: Exception){
                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
            }
        }
    }

    fun cartItemDecreaseQuantity(cartItem: CartItemEntity){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = if(cartItem.productQuantity>1){
                    val updatedCartItem = cartItem.copy(
                        productQuantity = cartItem.productQuantity-1
                    )
                    repository.updateCartItem(updatedCartItem)
                }else{
                    repository.deleteCartItem(cartItem)
                }
                if(response !=0) {
                    _cartState.postValue(UiState.Success(response.toLong()))
                }else{
                    _cartState.postValue(UiState.Error("Couldn't able to find Cart Record with Id= ${cartItem.cartItemId}"))
                }
            }catch (e: Exception){
                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
            }
        }
    }

    fun addToCart(cartProduct: CartProduct){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = LoginPreferences.getUserId()
                val cartId: Long = repository.getActiveCartById(userId.toLong())?: return@launch
                val cartItem = CartItemEntity(
                    productId = cartProduct.productId,
                    productName = cartProduct.productName,
                    productDescription = cartProduct.description,
                    productPrice = cartProduct.price,
                    productQuantity = 1,
                    productImageURl = cartProduct.imageUrl,
                    cartId = cartId
                )
                repository.insertCartItem(cartItemEntity = cartItem)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun makeCartInactive(cartId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.makeCartInactive(cartId)

                if(response == 0){
                    _cartState.postValue(
                        UiState.Error("Couldn't able to update cart")
                    )
                }

            }catch (e: Exception){
                _cartState.postValue(
                    UiState.Error(e.message ?: "Something went wrong")
                )
            }
        }
    }

    class CartViewModelFactory(val repository: CartRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CartViewModel::class.java)){
                return CartViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel for type CartViewModel")
        }
    }
}