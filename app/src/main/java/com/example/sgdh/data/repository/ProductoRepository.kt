package com.example.sgdh.data.repository

import com.example.sgdh.data.models.Producto
import com.example.sgdh.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductoRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getProductos(
        search: String? = null,
        cuadroBasico: Boolean? = null
    ): Result<List<Producto>> {
        return try {
            val response = apiService.getProductos(search, cuadroBasico)

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Result.success(body.data)
            } else {
                Result.failure(Exception("Error al cargar productos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}