package com.example.sgdh.ui.state

import com.example.sgdh.data.models.productos.Producto

data class ProductoListState(
    val productos: List<Producto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val filteredProductos: List<Producto> = emptyList()
)