package com.example.sgdh.ui.solicitudes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sgdh.data.models.solicitudes.Solicitud
import com.example.sgdh.databinding.ItemSolicitudBinding
import com.example.sgdh.util.DateUtils

class SolicitudAdapter(
    private val onItemClick: (Solicitud) -> Unit
) : ListAdapter<Solicitud, SolicitudAdapter.SolicitudViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitudViewHolder {
        val binding = ItemSolicitudBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SolicitudViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SolicitudViewHolder, position: Int) {
        val solicitud = getItem(position)
        holder.bind(solicitud)
    }

    inner class SolicitudViewHolder(private val binding: ItemSolicitudBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(solicitud: Solicitud) {
            binding.tvSolicitudId.text = "Solicitud #${solicitud.id}"
            binding.tvEstado.text = "Estado: ${solicitud.estatus.label}"
            binding.tvFecha.text = "Fecha: ${DateUtils.formatDate(solicitud.fecha_solicitud)}"
            binding.tvJustificacion.text = "Justificación: ${solicitud.justificacion}"
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Solicitud>() {
        override fun areItemsTheSame(oldItem: Solicitud, newItem: Solicitud): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Solicitud, newItem: Solicitud): Boolean {
            return oldItem == newItem
        }
    }
}