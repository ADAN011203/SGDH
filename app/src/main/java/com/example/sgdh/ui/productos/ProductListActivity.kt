package com.example.sgdh.ui.productos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sgdh.databinding.ActivityProductListBinding
import com.example.sgdh.ui.productos.list.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupListeners()

        viewModel.getProductos()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupRecyclerView() {
        adapter = ProductoAdapter()
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { state ->
            binding.progressBar.visibility = if (state.isLoading) android.view.View.VISIBLE else android.view.View.GONE

            if (state.error != null) {
                binding.tvError.visibility = android.view.View.VISIBLE
                binding.tvError.text = state.error
                binding.rvProductos.visibility = android.view.View.GONE
                binding.tvEmpty.visibility = android.view.View.GONE
            } else if (state.productos.isEmpty()) {
                binding.tvEmpty.visibility = android.view.View.VISIBLE
                binding.tvEmpty.text = "No hay productos disponibles"
                binding.rvProductos.visibility = android.view.View.GONE
                binding.tvError.visibility = android.view.View.GONE
            } else {
                binding.rvProductos.visibility = android.view.View.VISIBLE
                adapter.submitList(state.productos)
                binding.tvError.visibility = android.view.View.GONE
                binding.tvEmpty.visibility = android.view.View.GONE
            }
        }
    }

    private fun setupListeners() {
        binding.btnSearch.setOnClickListener {
            val searchQuery = binding.etSearch.text.toString()
            viewModel.searchProductos(searchQuery)
        }

        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            val searchQuery = binding.etSearch.text.toString()
            viewModel.searchProductos(searchQuery)
            true
        }
    }
}