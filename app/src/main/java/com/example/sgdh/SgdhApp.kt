package com.example.sgdh

import android.app.Application
import com.example.sgdh.data.remote.RetrofitClient

// REMOVER: @HiltAndroidApp
class SgdhApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitClient.initialize(this)
    }
}