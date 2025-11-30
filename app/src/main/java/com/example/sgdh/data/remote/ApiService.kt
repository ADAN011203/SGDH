package com.example.sgdh.data.remote

import com.example.sgdh.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Auth
    @POST("auth/token")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/google")
    suspend fun loginWithGoogle(@Body request: GoogleLoginRequest): Response<AuthResponse>

    @DELETE("auth/token")
    suspend fun logout(): Response<Unit>

    // Solicitudes
    @GET("solicitudes")
    suspend fun getSolicitudes(
        @Query("per_page") perPage: Int = 15,
        @Query("estatus") estatus: String? = null
    ): Response<PaginatedResponse<Solicitud>>

    @GET("solicitudes/{id}")
    suspend fun getSolicitud(@Path("id") id: Int): Response<ApiResponse<Solicitud>>

    @POST("solicitudes")
    suspend fun createSolicitud(@Body request: CreateSolicitudRequest): Response<ApiResponse<Solicitud>>

    @PATCH("solicitudes/{id}/estatus")
    suspend fun updateSolicitudStatus(
        @Path("id") id: Int,
        @Body request: UpdateStatusRequest
    ): Response<ApiResponse<Solicitud>>

    // Productos
    @GET("productos")
    suspend fun getProductos(
        @Query("per_page") perPage: Int = 50,
        @Query("search") search: String? = null
    ): Response<PaginatedResponse<DotacionProducto>>

    // Notificaciones
    @GET("notifications")
    suspend fun getNotifications(
        @Query("limit") limit: Int = 25,
        @Query("unread") unread: Boolean? = null
    ): Response<NotificacionesResponse>

    @PATCH("notifications/{id}")
    suspend fun markNotificationAsRead(@Path("id") id: String): Response<ApiResponse<Notificacion>>
}