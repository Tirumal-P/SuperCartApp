package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.remote.response.Product
import com.example.supercartapp.repository.ProductRepository
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsViewModel(val repository: ProductRepository): ViewModel() {

    private val _productDetails = MutableLiveData<UiState<Product>>()

    val productDetails : LiveData<UiState<Product>>
        get() = _productDetails

    val existingProductDetails = mutableMapOf<Int, Product>()

    fun getProductDetails(productId: Int){
        viewModelScope.launch (Dispatchers.IO){
            if(!existingProductDetails.containsKey(productId)) {
                try {
                    _productDetails.postValue(UiState.Loading)
                    val response = repository.getProductDetails(productId)
                    if (response.status != 0) {
                        _productDetails.postValue(UiState.Error(response.message))
                    } else {
                        _productDetails.postValue(UiState.Success(response.product))
                        existingProductDetails[productId] = response.product
                    }
                } catch (e: Exception) {
                    _productDetails.postValue(UiState.Error(e.message ?: "Something went wrong"))
                }
            }else{
                existingProductDetails[productId]?.let {
                    _productDetails.postValue(UiState.Success(it))
                }
            }
        }
    }

    class ProductDetailsViewModelFactory(val repository: ProductRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)){
                return ProductDetailsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknow ViewModel for type ProductDetailsViewModel")
        }
    }
}