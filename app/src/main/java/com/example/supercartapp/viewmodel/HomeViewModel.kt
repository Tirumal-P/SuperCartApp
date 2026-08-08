package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.response.CategoryItem
import com.example.supercartapp.repository.SuperCartRepository
import com.example.supercartapp.util.ApiType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val repository: SuperCartRepository): ViewModel() {

    private val _categories = MutableLiveData<List<CategoryItem>>()
    val categories: LiveData<List<CategoryItem>>
        get() = _categories

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    private val _processLoading = MutableLiveData<Boolean>()
    val processLoading: LiveData<Boolean>
        get() = _processLoading

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _processLoading.postValue(true)
                val categoryResponse = repository.getCategories()
                if(categoryResponse.status!=0){
                    _errorLiveData.postValue("${categoryResponse.message} to Get ${ApiType.CATEGORY.displayName} ")
                }else{
                    _categories.postValue(categoryResponse.categories)
                }
            }catch (e: Error){
                throw e
            }finally {
                _processLoading.postValue(false)
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

