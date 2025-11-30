package com.example.sgdh.ui.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sgdh.data.models.productos.ProductoData
import com.example.sgdh.databinding.ItemProductoBinding

class ProductoAdapter : ListAdapter<ProductoData, ProductoAdapter.ProductoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val productoData = getItem(position)
        holder.bind(productoData)
    }

    inner class ProductoViewHolder(private val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productoData: ProductoData) {
            val producto = productoData.producto
            binding.tvDescripcion.text = producto.descripcion
            binding.tvClave.text = "Clave: ${producto.clave}"
            binding.tvPresentacion.text = "Presentación: ${producto.presentacion}"
            binding.tvStock.text = "Stock: ${productoData.stock_disponible ?: 0}"

            productoData.dotacion?.cantidad_diaria?.let { dotacion ->
                binding.tvDotacion.text = "Dotación diaria: $dotacion"
                binding.tvDotacion.visibility = android.view.View.VISIBLE
            } ?: run {
                binding.tvDotacion.visibility = android.view.View.GONE
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<ProductoData>() {
        override fun areItemsTheSame(oldItem: ProductoData, newItem: ProductoData): Boolean {
            return oldItem.producto.id == newItem.producto.id
        }

        override fun areContentsTheSame(oldItem: ProductoData, newItem: ProductoData): Boolean {
            return oldItem == newItem
        }
    }
}