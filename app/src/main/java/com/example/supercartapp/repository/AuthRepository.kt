package com.example.supercartapp.repository

import com.example.supercartapp.model.remote.request.LoginRequest
import com.example.supercartapp.model.remote.request.LoginResponse
import com.example.supercartapp.model.remote.request.RegisterRequest
import com.example.supercartapp.model.remote.request.RegisterResponse

interface AuthRepository {

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse

    suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse

    suspend fun saveLogin(userId: Int)

    suspend fun isLoggedIn(): Boolean

    suspend fun getUserId(): Int?

    suspend fun logout()
}