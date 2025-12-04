package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

// ============ Auth Response ============
data class AuthResponse(
    @SerializedName("token")
    val token: String,

    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("role")
    val role: String?
)

// ============ Productos Response ============
data class ProductosResponse(
    @SerializedName("data")
    val data: List<Producto>
)

// ============ Solicitudes Response ============
data class SolicitudesResponse(
    @SerializedName("data")
    val data: List<Solicitud>
)

data class SolicitudResponse(
    @SerializedName("data")
    val data: Solicitud
)

// ============ Notificaciones Response ============
data class NotificacionesResponse(
    @SerializedName("data")
    val data: List<Notificacion>,

    @SerializedName("meta")
    val meta: NotificacionesMeta?
)

data class NotificacionesMeta(
    @SerializedName("unread_count")
    val unreadCount: Int
)