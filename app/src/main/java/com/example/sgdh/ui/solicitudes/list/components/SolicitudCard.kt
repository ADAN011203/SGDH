package com.example.sgdh.ui.solicitudes.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sgdh.data.models.Solicitud
import com.example.sgdh.data.models.SolicitudStatus
import com.example.sgdh.util.toFormattedDate

@Composable
fun SolicitudCard(
    solicitud: Solicitud,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: ID y Estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Solicitud #${solicitud.id}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                StatusChip(estatus = solicitud.estatus)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Área
            solicitud.area?.let { area ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = area.nombre,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Fecha
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = solicitud.fechaSolicitud.toFormattedDate(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Justificación
            Text(
                text = solicitud.justificacion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Cantidad de productos
            solicitud.detalles?.let { detalles ->
                if (detalles.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Inventory,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${detalles.size} producto(s)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(estatus: String) {
    val (backgroundColor, contentColor, icon, text) = when (estatus) {
        SolicitudStatus.PENDIENTE_JEFE.value -> {
            StatusInfo(
                Color(0xFFFFF3E0),
                Color(0xFFE65100),
                Icons.Default.PendingActions,
                "Pendiente Jefe"
            )
        }
        SolicitudStatus.PENDIENTE_FARMACIA.value -> {
            StatusInfo(
                Color(0xFFE3F2FD),
                Color(0xFF1565C0),
                Icons.Default.HourglassEmpty,
                "Pendiente Farmacia"
            )
        }
        SolicitudStatus.APROBADA.value -> {
            StatusInfo(
                Color(0xFFE8F5E9),
                Color(0xFF2E7D32),
                Icons.Default.CheckCircle,
                "Aprobada"
            )
        }
        SolicitudStatus.RECHAZADA.value -> {
            StatusInfo(
                Color(0xFFFFEBEE),
                Color(0xFFC62828),
                Icons.Default.Cancel,
                "Rechazada"
            )
        }
        SolicitudStatus.SURTIDA.value -> {
            StatusInfo(
                Color(0xFFE1F5FE),
                Color(0xFF01579B),
                Icons.Default.Done,
                "Surtida"
            )
        }
        else -> {
            StatusInfo(
                Color(0xFFF5F5F5),
                Color(0xFF616161),
                Icons.Default.HelpOutline,
                estatus
            )
        }
    }

    Surface(
        shape = MaterialTheme.shapes.small,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = contentColor
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private data class StatusInfo(
    val backgroundColor: Color,
    val contentColor: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val text: String
)