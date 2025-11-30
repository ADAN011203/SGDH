package com.example.sgdh.data.repository

import com.example.sgdh.data.models.Notificacion
import com.example.sgdh.data.models.NotificacionesResponse
import com.example.sgdh.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificacionRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getNotifications(
        limit: Int = 25,
        unread: Boolean? = null
    ): Result<NotificacionesResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getNotifications(limit, unread)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener notificaciones"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun markAsRead(notificationId: String): Result<Notificacion> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.markNotificationAsRead(notificationId)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.data)
                } else {
                    Result.failure(Exception("Error al marcar notificación"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}