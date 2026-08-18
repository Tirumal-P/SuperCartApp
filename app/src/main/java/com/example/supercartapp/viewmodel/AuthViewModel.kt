package com.example.supercartapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.supercartapp.model.remote.request.LoginRequest
import com.example.supercartapp.model.remote.response.LoginResponse
import com.example.supercartapp.model.remote.request.RegisterRequest
import com.example.supercartapp.model.remote.response.RegisterResponse
import com.example.supercartapp.repository.AuthRepository
import com.example.supercartapp.repository.CartRepository
import com.example.supercartapp.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(val authRepository: AuthRepository, val cartRepository: CartRepository): ViewModel() {

    private val _loginState = MutableLiveData<UiState<LoginResponse>>()
    val loginState: LiveData<UiState<LoginResponse>>
        get() = _loginState

    private val _registerState = MutableLiveData<UiState<RegisterResponse>>()
    val registerState: LiveData<UiState<RegisterResponse>>
        get() = _registerState

    fun loginUser(email: String, password: String){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loginState.postValue(UiState.Loading)
                val loginRequest = LoginRequest(email,password)
                val response = authRepository.loginUser(loginRequest)
                if(response.status==0){
                    if(response.user != null){
                        val user = response.user
                        authRepository.saveLogin(user.userId.toInt(), user.fullName, user.emailId, user.mobileNo)
                        cartRepository.ensureActiveCart(response.user.userId.toLong())
                        _loginState.postValue(UiState.Success(response))
                    }else{
                        _loginState.postValue(UiState.Error("Error in Database with User Login"))
                    }
                }else{
                    _loginState.postValue(UiState.Error(response.message))
                }
            }catch (e: Exception){
                _loginState.postValue(UiState.Error(e.message?:"Something Went Wrong"))
            }
        }
    }

    fun registerUser(email: String, password: String, fullName: String, mobileNumber: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val registerRequest = RegisterRequest(
                    emailId = email,
                    password = password,
                    fullName = fullName,
                    mobileNo = mobileNumber,
                )
                _registerState.postValue(UiState.Loading)
                val response = authRepository.registerUser(registerRequest)
                if(response.status==0){
                    _registerState.postValue(UiState.Success(response))
                }else{
                    _registerState.postValue(UiState.Error(response.message))
                }
            }catch (e: Exception){
                _registerState.postValue(UiState.Error(e.message?:"Something Went Wrong"))
            }
        }
    }


    class AuthViewModelFactory(val authRepository: AuthRepository, val cartRepository: CartRepository): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
                return AuthViewModel(authRepository, cartRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel for type AuthViewModel")
        }
    }
}