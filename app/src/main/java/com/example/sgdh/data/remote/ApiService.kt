package com.example.sgdh.data.remote

import com.example.sgdh.data.models.AuthResponse
import com.example.sgdh.data.models.CreateSolicitudRequest
import com.example.sgdh.data.models.GoogleLoginRequest
import com.example.sgdh.data.models.LoginRequest
import com.example.sgdh.data.models.NotificacionesResponse
import com.example.sgdh.data.models.ProductosResponse
import com.example.sgdh.data.models.SolicitudResponse
import com.example.sgdh.data.models.SolicitudesResponse
import com.example.sgdh.data.models.UpdateStatusRequest
import com.example.sgdh.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ============ Autenticación ============

    @POST("auth/token")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/google")
    suspend fun loginWithGoogle(@Body request: GoogleLoginRequest): Response<AuthResponse>

    @DELETE("auth/token")
    suspend fun logout(): Response<Unit>

    // ============ Productos ============

    @GET("productos")
    suspend fun getProductos(
        @Query("search") search: String? = null,
        @Query("cuadro_basico") cuadroBasico: Boolean? = null
    ): Response<ProductosResponse>

    // ============ Solicitudes ============

    @GET("solicitudes")
    suspend fun getSolicitudes(): Response<SolicitudesResponse>

    @POST("solicitudes")
    suspend fun createSolicitud(@Body request: CreateSolicitudRequest): Response<SolicitudResponse>

    @GET("solicitudes/{id}")
    suspend fun getSolicitudById(@Path("id") id: Int): Response<SolicitudResponse>

    @PATCH("solicitudes/{id}/estatus")
    suspend fun updateSolicitudStatus(
        @Path("id") id: Int,
        @Body request: UpdateStatusRequest
    ): Response<SolicitudResponse>

    // ============ Notificaciones ============

    @GET("notifications")
    suspend fun getNotifications(): Response<NotificacionesResponse>

    @PATCH("notifications/{id}")
    suspend fun markNotificationAsRead(@Path("id") id: String): Response<Unit>
}
// Agrega al final del archivo que ya tienes:

// DotacionProducto (para la creación de solicitudes)
data class DotacionProducto(
    val producto_id: Int,
    val cantidad: Int
)

// Para respuestas paginadas
data class PaginatedResponse<T>(
    val data: T,
    val meta: Meta? = null
)

data class Meta(
    val total: Int,
    val unread: Int? = null
)

// Notificaciones
data class NotificacionesResponse(
    val data: List<Notificacion>,
    val meta: Meta?
)

data class Notificacion(
    val id: Int,
    val title: String,
    val message: String,
    val read: Boolean,
    val created_at: String
)

// Para respuestas de listas
data class SolicitudesResponse(
    val solicitudes: List<Solicitud>
)

data class ProductosResponse(
    val productos: List<Producto>
)