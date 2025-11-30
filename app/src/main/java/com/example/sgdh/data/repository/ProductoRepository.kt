package com.example.sgdh.data.repository

import com.example.sgdh.data.models.DotacionProducto
import com.example.sgdh.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductoRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getProductos(
        perPage: Int = 50,
        search: String? = null
    ): Result<List<DotacionProducto>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProductos(perPage, search)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
            } else {
                Result.failure(Exception("Error al obtener productos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}