package com.example.sgdh.data.repository

import com.example.sgdh.data.models.Notificacion
import com.example.sgdh.data.models.NotificacionesResponse
import com.example.sgdh.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificacionRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getNotificaciones(): Result<NotificacionesResponse> {
        return try {
            val response = apiService.getNotifications()

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)  // Devuelve el response completo
            } else {
                Result.failure(Exception("Error al cargar notificaciones"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    /**
     * Obtiene todas las notificaciones del usuario
     */
/*    suspend fun getNotifications(): Result<List<Notificacion>> {
        return try {
            val response = apiService.getNotifications()

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Result.success(body.data)
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "No autorizado"
                    403 -> "Sin permisos"
                    else -> "Error al cargar notificaciones: ${response.code()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }*/

    /**
     * Marca una notificación como leída
     */
    suspend fun markAsRead(notificationId: String): Result<Unit> {
        return try {
            val response = apiService.markNotificationAsRead(notificationId)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al marcar como leída"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}