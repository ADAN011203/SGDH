package com.example.sgdh.ui.notifications

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sgdh.databinding.ActivityNotificationListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationListBinding
    private val viewModel: NotificationListViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupListeners()

        viewModel.getNotifications()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupRecyclerView() {
        adapter = NotificationAdapter { notification ->
            viewModel.markAsRead(notification.id)
        }
        binding.rvNotifications.layoutManager = LinearLayoutManager(this)
        binding.rvNotifications.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { state ->
            binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            binding.swipeRefresh.isRefreshing = state.isLoading

            if (state.error != null) {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = state.error
                binding.rvNotifications.visibility = View.GONE
                binding.tvEmpty.visibility = View.GONE
            } else if (state.notifications.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.tvEmpty.text = "No hay notificaciones"
                binding.rvNotifications.visibility = View.GONE
                binding.tvError.visibility = View.GONE
            } else {
                binding.rvNotifications.visibility = View.VISIBLE
                adapter.submitList(state.notifications)
                binding.tvError.visibility = View.GONE
                binding.tvEmpty.visibility = View.GONE
            }

            // Actualizar contador
            binding.tvUnreadCount.text = "No leídas: ${state.unreadCount}"
        }

        viewModel.markAsReadResult.observe(this) { result ->
            result?.let {
                if (it.isSuccess) {
                    viewModel.getNotifications()
                } else {
                    Toast.makeText(this, "Error al marcar como leída", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getNotifications()
        }

        binding.btnMarkAllRead.setOnClickListener {
            viewModel.markAllAsRead()
        }
    }
}