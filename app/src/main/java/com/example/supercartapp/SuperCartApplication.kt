package com.example.supercartapp

import android.app.Application
import com.example.supercartapp.model.local.preferences.LoginPreferences

class SuperCartApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        LoginPreferences.init(this)
    }
}