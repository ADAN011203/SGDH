package com.example.sgdh.data.models

// ============ Estados de Solicitud ============

enum class SolicitudStatus(val value: String, val displayName: String) {
    PENDIENTE_JEFE("Pendiente_Jefe", "Pendiente Jefe"),
    PENDIENTE_FARMACIA("Pendiente_Farmacia", "Pendiente Farmacia"),
    APROBADA("Aprobada", "Aprobada"),
    RECHAZADA("Rechazada", "Rechazada"),
    SURTIDA("Surtida", "Surtida");

    companion object {
        fun fromValue(value: String): SolicitudStatus {
            return values().find { it.value == value } ?: PENDIENTE_JEFE
        }
    }
}

// ============ Roles de Usuario ============

enum class UserRole(val value: String, val displayName: String) {
    SUPER_ADMIN("super_admin", "Super Admin"),
    ADMIN_FARMACIA("admin_farmacia", "Admin Farmacia"),
    JEFE_AREA("jefe_area", "Jefe de Área"),
    PERSONAL_AREA("personal_area", "Personal de Área");

    companion object {
        fun fromValue(value: String): UserRole {
            return values().find { it.value == value } ?: PERSONAL_AREA
        }
    }
}