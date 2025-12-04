package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

data class Solicitud(
    @SerializedName("id")
    val id: Int,

    @SerializedName("area_id")
    val areaId: Int,

    @SerializedName("justificacion")
    val justificacion: String,

    @SerializedName("estatus")
    val estatus: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("detalles")
    val detalles: List<DetalleSolicitud>? = null
)

data class DetalleSolicitud(
    @SerializedName("id")
    val id: Int,

    @SerializedName("producto_id")
    val productoId: Int,

    @SerializedName("cantidad_solicitada")
    val cantidadSolicitada: Int,

    @SerializedName("cantidad_aprobada")
    val cantidadAprobada: Int?,

    @SerializedName("producto")
    val producto: Producto?
)

