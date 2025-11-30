package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

data class Solicitud(
    @SerializedName("id")
    val id: Int,

    @SerializedName("area_id")
    val areaId: Int,

    @SerializedName("usuario_solicitante_id")
    val usuarioSolicitanteId: Int,

    @SerializedName("fecha_solicitud")
    val fechaSolicitud: String,

    @SerializedName("justificacion")
    val justificacion: String,

    @SerializedName("estatus")
    val estatus: String,

    @SerializedName("last_modified_by_user_id")
    val lastModifiedByUserId: Int?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,

    @SerializedName("area")
    val area: Area?,

    @SerializedName("usuario_solicitante")
    val usuarioSolicitante: User?,

    @SerializedName("detalles")
    val detalles: List<SolicitudDetalle>?
)

data class SolicitudDetalle(
    @SerializedName("id")
    val id: Int,

    @SerializedName("solicitud_id")
    val solicitudId: Int,

    @SerializedName("producto_id")
    val productoId: Int,

    @SerializedName("cantidad_solicitada")
    val cantidadSolicitada: Int,

    @SerializedName("cantidad_entregada")
    val cantidadEntregada: Int?,

    @SerializedName("producto")
    val producto: Producto?
)

data class Area(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("descripcion")
    val descripcion: String?
)

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("rol")
    val rol: String?,

    @SerializedName("area_id")
    val areaId: Int?
)

// Enums para Status
enum class SolicitudStatus(val value: String) {
    PENDIENTE_JEFE("pendiente_jefe"),
    PENDIENTE_FARMACIA("pendiente_farmacia"),
    APROBADA("aprobada"),
    RECHAZADA("rechazada"),
    SURTIDA("surtida");

    companion object {
        fun fromString(value: String): SolicitudStatus? {
            return values().find { it.value == value }
        }
    }
}