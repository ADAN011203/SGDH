package com.example.sgdh.ui.solicitudes.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sgdh.data.models.productos.Producto

@Composable
fun ProductoSelection(
    producto: Producto,
    cantidad: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Clave: ${producto.clave} - ${producto.presentacion}",
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onDecrement,
                    enabled = cantidad > 0
                ) {
                    Text("-")
                }

                Text(
                    text = "Cantidad: $cantidad",
                    style = MaterialTheme.typography.bodyMedium
                )

                Button(
                    onClick = onIncrement
                ) {
                    Text("+")
                }
            }
        }
    }
}