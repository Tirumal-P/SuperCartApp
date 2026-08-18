package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.request.LoginRequest
import com.example.supercartapp.model.remote.response.LoginResponse
import com.example.supercartapp.model.remote.request.RegisterRequest
import com.example.supercartapp.model.remote.response.RegisterResponse

interface AuthRepository {

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse

    suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse

    suspend fun saveLogin(userId: Int, name: String, email: String, phone: String)

    suspend fun isLoggedIn(): Boolean

    suspend fun getUserId(): Int?

    suspend fun logout()
}