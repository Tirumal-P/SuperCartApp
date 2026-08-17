package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.local.entity.CartEntity
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

    fun insertCartItem(cartItem: CartItemEntity){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cartState.postValue(UiState.Loading)
                val response = repository.insertCartItem(cartItem)
                _cartState.postValue(UiState.Success(response))
            }catch (e: Exception){
                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
            }
        }
    }

    fun updateCartItem(cartItem: CartItemEntity){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cartState.postValue(UiState.Loading)
                val response = repository.updateCartItem(cartItem)
                if(response !=0) {
                    _cartState.postValue(UiState.Success(response.toLong()))
                }else{
                    _cartState.postValue(UiState.Error("Couldn't able to find cartItem Record with Id= ${cartItem.cartItemId}"))
                }
            }catch (e: Exception){
                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
            }
        }
    }

    fun deleteCartItem(cartItem: CartItemEntity){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cartState.postValue(UiState.Loading)
                val response = repository.deleteCartItem(cartItem)
                if(response !=0) {
                    _cartState.postValue(UiState.Success(response.toLong()))
                }else{
                    _cartState.postValue(UiState.Error("Couldn't able to find cartItem Record with Id= ${cartItem.cartItemId}"))
                }
            }catch (e: Exception){
                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
            }
        }
    }

    fun insertCart(cartEntity: CartEntity){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cartState.postValue(UiState.Loading)
                val response = repository.insertCart(cartEntity)
                _cartState.postValue(UiState.Success(response))
            }catch (e: Exception){
                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
            }
        }
    }

    fun updateCart(cartEntity: CartEntity){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cartState.postValue(UiState.Loading)
                val response = repository.updateCart(cartEntity)
                if(response !=0) {
                    _cartState.postValue(UiState.Success(response.toLong()))
                }else{
                    _cartState.postValue(UiState.Error("Couldn't able to find Cart Record with Id= ${cartEntity.cartId}"))
                }
            }catch (e: Exception){
                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
            }
        }
    }

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

//    fun getCartId(userId: Int):Long{
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = repository.getActiveCartById(1L)
//                return@get userId
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//    }

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

//    fun getCart(){
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                _cartState.postValue(UiState.Loading)
//                val response = repository.getCartWithCartItemsByUserId(1)
//                if(response != null){
//                    _cartState.postValue(UiState.Success(response))
//                }else{
//                    _cartState.postValue(UiState.Error("Your Cart is Currently Empty"))
//                }
//            }catch (e: Exception){
//                _cartState.postValue(UiState.Error(e.message?:"Something went wrong."))
//            }
//        }
//    }

    class CartViewModelFactory(val repository: CartRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CartViewModel::class.java)){
                return CartViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel for type CartViewModel")
        }
    }
}