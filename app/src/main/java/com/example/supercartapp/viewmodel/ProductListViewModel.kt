package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.response.ProductItem
import com.example.supercartapp.model.response.SubcategoryItem
import com.example.supercartapp.repository.SuperCartRepository
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(val repository: SuperCartRepository): ViewModel(){

    private val _subCategories = MutableLiveData<UiState<List<SubcategoryItem>>>()
    val subCategories :LiveData<UiState<List<SubcategoryItem>>>
        get() = _subCategories

    private val _products = MutableLiveData<UiState<List<ProductItem>>>()
    val products :LiveData<UiState<List<ProductItem>>>
        get() = _products

    private val existingSubCategories = mutableMapOf<Int, List<SubcategoryItem>>()

    private val existingProducts = mutableMapOf<Int, List<ProductItem>>()

    fun getSubCategories(categoryId: Int){
        if(!existingSubCategories.containsKey(categoryId)) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _subCategories.postValue(UiState.Loading)
                    val subCategoriesResponse = repository.getSubCategories(categoryId)
                    if (subCategoriesResponse.status != 0) {
                        _subCategories.postValue(UiState.Error(subCategoriesResponse.message))
                    } else {
                        _subCategories.postValue(UiState.Success(subCategoriesResponse.subcategories))
                        existingSubCategories[categoryId] = subCategoriesResponse.subcategories
                    }
                } catch (e: Exception) {
                    _subCategories.postValue(UiState.Error(e.message?:"Something Went Wrong"))
                }
            }
        }else{
            existingSubCategories[categoryId]?.let {
                _subCategories.postValue(UiState.Success(it))
            }
        }
    }
    fun getProducts(subCategoryId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            if(!existingProducts.contains(subCategoryId)) {
                try {
                    _products.postValue(UiState.Loading)
                    val productResponse = repository.getProducts(subCategoryId)
                    if (productResponse.status != 0) {
                        _products.postValue(UiState.Error(productResponse.message))
                    } else {
                        _products.postValue(UiState.Success(productResponse.products))
                        existingProducts[subCategoryId] = productResponse.products
                    }
                } catch (e: Exception) {
                    _products.postValue(UiState.Error(e.message?:"Something Went Wrong"))
                }
            }else{
                existingProducts[subCategoryId]?.let {
                    _products.postValue(UiState.Success(it))
                }
            }
        }
    }

    class ProductListViewModelFactory(val repository: SuperCartRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ProductListViewModel::class.java)){
                return ProductListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknow ViewModel for type ProductListViewModel")
        }
    }
}