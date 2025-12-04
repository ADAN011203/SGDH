package com.example.sgdh.data.models

import com.google.gson.annotations.SerializedName

data class Producto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("clave")
    val clave: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("presentacion")
    val presentacion: String?,

    @SerializedName("image_path")
    val imagePath: String?,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("cuadro_basico")
    val cuadroBasico: Boolean?,

    @SerializedName("is_active")
    val isActive: Boolean = true,

    @SerializedName("stock_min")
    val stockMin: Int?,

    @SerializedName("stock_max")
    val stockMax: Int?,

    @SerializedName("stock_optimo")
    val stockOptimo: Int?,

    @SerializedName("stock_total")
    val stockTotal: Int = 0,

    @SerializedName("stock_disponible")
    val stockDisponible: Int = 0,

    @SerializedName("last_modified_by_user_id")
    val lastModifiedByUserId: Int?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("updated_at")
    val updatedAt: String?
) {
    val nombre: String
        get() = "$clave - $descripcion"
}
/*data class ProductosResponse(
    @SerializedName("data")
    val data: List<Producto>
)*/

data class DotacionProducto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("area_id")
    val areaId: Int,

    @SerializedName("producto_id")
    val productoId: Int,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("producto")
    val producto: Producto?
)