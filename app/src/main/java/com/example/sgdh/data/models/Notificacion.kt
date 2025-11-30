package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

data class Notificacion(
    @SerializedName("id")
    val id: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("data")
    val data: Map<String, Any>,

    @SerializedName("read_at")
    val readAt: String?,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)

data class NotificacionesResponse(
    @SerializedName("data")
    val data: List<Notificacion>,

    @SerializedName("meta")
    val meta: NotificacionMeta
)

data class NotificacionMeta(
    @SerializedName("unread_count")
    val unreadCount: Int
)