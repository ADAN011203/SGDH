package com.example.sgdh.ui.solicitudes.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sgdh.data.models.DetalleSolicitud
import com.example.sgdh.databinding.ItemProductoDetalleBinding

class ProductosDetalleAdapter : ListAdapter<DetalleSolicitud, ProductosDetalleAdapter.ViewHolder>(DetalleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductoDetalleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemProductoDetalleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(detalle: DetalleSolicitud) {
            binding.apply {
                // Nombre del producto
                textViewProductoNombre.text = detalle.producto?.nombre ?: "Producto #${detalle.productoId}"

                // Presentación
                detalle.producto?.presentacion?.let {
                    textViewProductoPresentacion.text = it
                }

                // Cantidad solicitada
                textViewCantidad.text = "Solicitado: ${detalle.cantidadSolicitada}"

                // Cantidad aprobada (si existe)
                detalle.cantidadAprobada?.let {
                    textViewCantidad.text = "Aprobado: $it"
                }
            }
        }
    }

    private class DetalleDiffCallback : DiffUtil.ItemCallback<DetalleSolicitud>() {
        override fun areItemsTheSame(oldItem: DetalleSolicitud, newItem: DetalleSolicitud): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetalleSolicitud, newItem: DetalleSolicitud): Boolean {
            return oldItem == newItem
        }
    }
}