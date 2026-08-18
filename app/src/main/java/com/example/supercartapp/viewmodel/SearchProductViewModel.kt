package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.remote.response.ProductX
import com.example.supercartapp.repository.ProductRepository
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _searchState = MutableLiveData<UiState< List<ProductX>>>()
    val searchState: LiveData<UiState<List<ProductX>>>
        get() = _searchState

    fun searchProduct(searchText: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchState.postValue(UiState.Loading)

                val response = repository.searchProduct(searchText)

                if(response.status == 0){
                    if(response.products.isEmpty()){
                        _searchState.postValue(UiState.Error("Couldn't able to find product for $searchText"))
                    }else {
                        _searchState.postValue(UiState.Success(response.products))
                    }
                }else{
                    _searchState.postValue(UiState.Error(response.message))
                }
            }catch (e: Exception){
                _searchState.postValue(
                    UiState.Error(e.message ?: "Something went wrong")
                )
            }
        }
    }

    class SearchViewModelFactory(private val repository: ProductRepository): ViewModelProvider.NewInstanceFactory(){

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchProductViewModel::class.java)){
                return SearchProductViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel for type SearchProductViewModel")
        }
    }
}