package com.example.sgdh.data.repository

import com.example.sgdh.data.models.*
import com.example.sgdh.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SolicitudRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getSolicitudes(
        perPage: Int = 15,
        estatus: String? = null
    ): Result<List<Solicitud>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getSolicitudes(perPage, estatus)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception("Error al obtener solicitudes"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSolicitud(id: Int): Result<Solicitud> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getSolicitud(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception("Error al obtener solicitud"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createSolicitud(request: CreateSolicitudRequest): Result<Solicitud> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.createSolicitud(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.data)
                } else {
                    Result.failure(Exception("Error al crear solicitud"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun updateStatus(
        id: Int,
        estatus: String,
        motivoRechazo: String? = null
    ): Result<Solicitud> = withContext(Dispatchers.IO) {
        try {
            val request = UpdateStatusRequest(estatus, motivoRechazo)
            val response = apiService.updateSolicitudStatus(id, request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception("Error al actualizar estatus"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}