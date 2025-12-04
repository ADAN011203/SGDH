package com.example.sgdh.ui.solicitudes.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sgdh.data.models.Solicitud
import com.example.sgdh.databinding.ItemSolicitudBinding

class SolicitudAdapter(
    private val onItemClick: (Solicitud) -> Unit
) : ListAdapter<Solicitud, SolicitudAdapter.ViewHolder>(SolicitudDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSolicitudBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemSolicitudBinding,
        private val onItemClick: (Solicitud) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(solicitud: Solicitud) {
            binding.apply {
                tvSolicitudId.text = "Solicitud #${solicitud.id}"
                tvEstatus.text = solicitud.estatus
                tvFecha.text = solicitud.createdAt
                tvJustificacion.text = solicitud.justificacion

                root.setOnClickListener {
                    onItemClick(solicitud)
                }
            }
        }
    }

    private class SolicitudDiffCallback : DiffUtil.ItemCallback<Solicitud>() {
        override fun areItemsTheSame(oldItem: Solicitud, newItem: Solicitud): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Solicitud, newItem: Solicitud): Boolean {
            return oldItem == newItem
        }
    }
}