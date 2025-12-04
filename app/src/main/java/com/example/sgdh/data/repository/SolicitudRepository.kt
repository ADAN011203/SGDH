package com.example.sgdh.data.repository

import com.example.sgdh.data.models.*
import com.example.sgdh.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SolicitudRepository @Inject constructor(
    private val apiService: ApiService
) {
    /**
     * Obtiene todas las solicitudes del usuario
     */
    suspend fun getSolicitudes(): Result<List<Solicitud>> {
        return try {
            val response = apiService.getSolicitudes()

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "No autorizado"
                    403 -> "Sin permisos"
                    else -> "Error al cargar solicitudes: ${response.code()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Obtiene una solicitud por ID
     */
    suspend fun getSolicitudById(id: Int): Result<Solicitud> {
        return try {
            val response = apiService.getSolicitudById(id)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                val errorMessage = when (response.code()) {
                    404 -> "Solicitud no encontrada"
                    401 -> "No autorizado"
                    403 -> "Sin permisos para ver esta solicitud"
                    else -> "Error al cargar solicitud: ${response.code()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Crea una nueva solicitud
     */
    suspend fun createSolicitud(
        justificacion: String,
        detalles: List<DetalleRequest>
    ): Result<Solicitud> {
        return try {
            val request = CreateSolicitudRequest(justificacion, detalles)
            val response = apiService.createSolicitud(request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                val errorMessage = when (response.code()) {
                    400 -> "Datos inválidos"
                    401 -> "No autorizado"
                    422 -> "Validación fallida"
                    else -> "Error al crear solicitud: ${response.code()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Actualiza el estado de una solicitud
     */
    suspend fun updateSolicitudStatus(
        solicitudId: Int,
        estatus: String,
        comentario: String? = null
    ): Result<Solicitud> {
        return try {
            val request = UpdateStatusRequest(estatus, comentario)
            val response = apiService.updateSolicitudStatus(solicitudId, request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                val errorMessage = when (response.code()) {
                    400 -> "Estado inválido"
                    401 -> "No autorizado"
                    403 -> "Sin permisos para actualizar"
                    404 -> "Solicitud no encontrada"
                    else -> "Error al actualizar estado: ${response.code()}"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Obtiene los productos disponibles
     */
    suspend fun getProductos(search: String? = null): Result<List<Producto>> {
        return try {
            val response = apiService.getProductos(search = search)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception("Error al cargar productos: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}