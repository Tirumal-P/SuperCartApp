package com.example.supercartapp.util

fun String.validateEmail(): Boolean{
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return this.matches(emailRegex)
}

fun String.validatePassword(): Boolean{
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$".toRegex()
    return this.matches(passwordRegex)
}

fun String.validateFullName(): Boolean{
    val fullNameRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return this.matches(fullNameRegex)
}

fun String.validatePhoneNumber(): Boolean{
    val phoneNumberRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return this.matches(phoneNumberRegex)
}