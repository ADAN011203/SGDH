package com.example.sgdh.ui.state

import com.example.sgdh.data.models.Solicitud

data class SolicitudListState(
    val solicitudes: List<Solicitud> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val filterStatus: String = "Todos"
)

data class SolicitudDetailState(
    val solicitud: Solicitud? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class CreateSolicitudState(
    val justificacion: String = "",
    val selectedProductos: Map<Int, Int> = emptyMap(), // productoId -> cantidad
    val isLoading: Boolean = false,
    val isSolicitudCreated: Boolean = false,
    val error: String? = null
)