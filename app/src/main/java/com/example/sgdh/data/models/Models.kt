import com.google.gson.annotations.SerializedName

// Auth
data class AuthResponse(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val rol: String,
    @SerializedName("area_id") val areaId: Int?
)

// Producto
data class Producto(
    val id: Int,
    val clave: String,
    val descripcion: String,
    val presentacion: String,
    @SerializedName("cuadro_basico") val cuadroBasico: Boolean,
    @SerializedName("stock_min") val stockMin: Int?,
    @SerializedName("stock_max") val stockMax: Int?,
    @SerializedName("stock_optimo") val stockOptimo: Int?
)

// Área
data class Area(
    val id: Int,
    val nombre: String,
    val responsable: String?
)

// Solicitud
data class Solicitud(
    val id: Int,
    @SerializedName("area_id") val areaId: Int,
    @SerializedName("usuario_solicitante_id") val usuarioSolicitanteId: Int,
    @SerializedName("fecha_solicitud") val fechaSolicitud: String,
    val justificacion: String,
    val estatus: String,
    val area: Area?,
    @SerializedName("usuario_solicitante") val usuarioSolicitante: User?,
    val detalles: List<SolicitudDetalle> = emptyList()
)

data class SolicitudDetalle(
    val id: Int,
    @SerializedName("solicitud_id") val solicitudId: Int,
    @SerializedName("producto_id") val productoId: Int,
    @SerializedName("cantidad_solicitada") val cantidadSolicitada: Int,
    val producto: Producto?
)

// Request bodies
/*
data class LoginRequest(
    val email: String,
    val password: String
)

data class GoogleLoginRequest(
    @SerializedName("google_token") val googleToken: String
)

data class CreateSolicitudRequest(
    val justificacion: String,
    val detalles: List<SolicitudDetalleRequest>
)
*/

/*
data class SolicitudDetalleRequest(
    @SerializedName("producto_id") val productoId: Int,
    @SerializedName("cantidad_solicitada") val cantidadSolicitada: Int
)

data class UpdateStatusRequest(
    val estatus: String
)

// Response wrappers
data class ApiResponse<T>(
    val data: T
)

data class ProductosResponse(
    val productos: List<Producto>
)
*/

