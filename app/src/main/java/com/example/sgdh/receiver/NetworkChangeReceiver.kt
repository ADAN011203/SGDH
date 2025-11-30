package com.example.sgdh.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.sgdh.util.NetworkUtils

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val isConnected = NetworkUtils.isNetworkAvailable(context)

            // Emitir un evento local o mostrar notificación
            if (!isConnected) {
                // Mostrar notificación de sin conexión
                showNetworkNotification(context, false)
            } else {
                // Ocultar notificación de sin conexión
                showNetworkNotification(context, true)
            }
        }
    }

    private fun showNetworkNotification(context: Context, isConnected: Boolean) {
        // Implementar notificación del sistema
        // Esto puede expandirse según las necesidades
    }
}