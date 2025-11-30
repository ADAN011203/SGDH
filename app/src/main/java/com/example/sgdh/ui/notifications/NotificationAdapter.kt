package com.example.sgdh.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sgdh.data.models.notifications.Notification
import com.example.sgdh.databinding.ItemNotificationBinding
import com.example.sgdh.util.DateUtils

class NotificationAdapter(
    private val onMarkAsRead: (Notification) -> Unit
) : ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        holder.bind(notification)
    }

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val notification = getItem(adapterPosition)
                if (notification.read_at == null) {
                    onMarkAsRead(notification)
                }
            }
        }

        fun bind(notification: Notification) {
            binding.tvTitle.text = when (notification.type) {
                "solicitud_created" -> "Nueva solicitud"
                "solicitud_approved" -> "Solicitud aprobada"
                "solicitud_rejected" -> "Solicitud rechazada"
                else -> "Notificación"
            }

            binding.tvMessage.text = when (notification.type) {
                "solicitud_created" -> "Se ha creado una nueva solicitud"
                "solicitud_approved" -> "Tu solicitud ha sido aprobada"
                "solicitud_rejected" -> "Tu solicitud ha sido rechazada"
                else -> "Tienes una nueva notificación"
            }

            binding.tvDate.text = DateUtils.formatDate(notification.created_at)

            // Marcar como leída/no leída
            if (notification.read_at == null) {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, android.R.color.holo_blue_light))
                binding.tvStatus.text = "NUEVO"
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.holo_red_dark))
            } else {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, android.R.color.transparent))
                binding.tvStatus.text = "LEÍDO"
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.darker_gray))
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }
}