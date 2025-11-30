package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

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

    @SerializedName("motivo_rechazo")
    val motivoRechazo: String? = null
)