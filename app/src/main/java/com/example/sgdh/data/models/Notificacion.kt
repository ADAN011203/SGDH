package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

data class Notificacion(
    @SerializedName("id")
    val id: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("data")
    val data: NotificacionData,

    @SerializedName("read_at")
    val readAt: String?,

    @SerializedName("created_at")
    val createdAt: String
)

data class NotificacionData(
    @SerializedName("title")
    val title: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("solicitud_id")
    val solicitudId: Int?
)