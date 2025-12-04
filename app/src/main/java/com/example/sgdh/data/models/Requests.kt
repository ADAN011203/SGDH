package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

// ============ Auth Requests ============
data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

data class GoogleLoginRequest(
    @SerializedName("token")
    val token: String
)

// ============ Solicitud Requests ============
data class CreateSolicitudRequest(
    @SerializedName("justificacion")
    val justificacion: String,

    @SerializedName("detalles")
    val detalles: List<DetalleRequest>
)

data class DetalleRequest(
    @SerializedName("producto_id")
    val productoId: Int,

    @SerializedName("cantidad_solicitada")
    val cantidadSolicitada: Int
)

data class UpdateStatusRequest(
    @SerializedName("estatus")
    val estatus: String,

    @SerializedName("comentario")
    val comentario: String? = null
)