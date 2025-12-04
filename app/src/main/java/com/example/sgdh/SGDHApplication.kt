package com.example.sgdh

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SGDHApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inicialización adicional si es necesaria
    }
}