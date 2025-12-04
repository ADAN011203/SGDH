package com.example.sgdh.ui.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sgdh.data.models.DotacionProducto
import com.example.sgdh.databinding.ItemProductoBinding

class ProductoAdapter : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    private var productos: List<DotacionProducto> = emptyList()

    fun submitList(newProductos: List<DotacionProducto>) {
        productos = newProductos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(productos[position])
    }

    override fun getItemCount() = productos.size

    class ProductoViewHolder(
        private val binding: ItemProductoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dotacion: DotacionProducto) {
            dotacion.producto?.let { producto ->
                binding.tvNombre.text = producto.nombre
                binding.tvDescripcion.text = producto.presentacion ?: producto.descripcion
            }
        }
    }
}