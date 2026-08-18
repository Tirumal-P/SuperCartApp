package com.example.supercartapp.model.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object LoginPreferences {

    private const val PREF_NAME = "login_preferences"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_PHONE = "user_phone"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.applicationContext.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun saveLogin(userId: Int, name: String, email: String, phone: String) {
        sharedPreferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putInt(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_PHONE, phone)
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(
            KEY_IS_LOGGED_IN,
            false
        )
    }

    fun getUserId(): Int {
        return sharedPreferences.getInt(
            KEY_USER_ID,
            -1
        )
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    fun getUserPhone(): String? {
        return sharedPreferences.getString(KEY_USER_PHONE, null)
    }

    fun logout() {
        sharedPreferences.edit {
            clear()
        }
    }
}