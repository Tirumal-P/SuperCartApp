package com.example.supercartapp.repository

import com.example.supercartapp.model.local.preferences.LoginPreferences
import com.example.supercartapp.model.remote.ApiService
import com.example.supercartapp.model.remote.request.LoginRequest
import com.example.supercartapp.model.remote.request.LoginResponse
import com.example.supercartapp.model.remote.request.RegisterRequest
import com.example.supercartapp.model.remote.request.RegisterResponse

class AuthRepositoryImpl(val apiService: ApiService): AuthRepository {

    override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return apiService.loginUser(loginRequest)
    }

    override suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return apiService.registerUser(registerRequest)
    }

    override suspend fun saveLogin(userId: Int) {
        LoginPreferences.saveLogin(userId)
    }

    override suspend fun isLoggedIn(): Boolean {
        return LoginPreferences.isLoggedIn()
    }

    override suspend fun getUserId(): Int {
        return LoginPreferences.getUserId()
    }

    override suspend fun logout() {
        LoginPreferences.logout()
    }
}