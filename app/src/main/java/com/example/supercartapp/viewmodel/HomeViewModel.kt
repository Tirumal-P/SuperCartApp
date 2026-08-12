package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.response.CategoryItem
import com.example.supercartapp.repository.SuperCartRepository
import com.example.supercartapp.util.ApiType
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val repository: SuperCartRepository): ViewModel() {

    private val _categories = MutableLiveData<UiState<List<CategoryItem>>>()
    val categories: LiveData<UiState<List<CategoryItem>>>
        get() = _categories

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _categories.postValue(UiState.Loading)
                val categoryResponse = repository.getCategories()
                if(categoryResponse.status!=0){
                    _categories.postValue(UiState.Error("${categoryResponse.message} to Get ${ApiType.CATEGORY.displayName} "))
                }else{
                    _categories.postValue(UiState.Success(categoryResponse.categories))
                }
            }catch (e: Exception){
                _categories.postValue(UiState.Error(e.message?:"Something Went Wrong"))
            }
        }
    }
    class HomeViewModelFactory(val repository: SuperCartRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel for type HomeViewModel")
        }
    }
}

