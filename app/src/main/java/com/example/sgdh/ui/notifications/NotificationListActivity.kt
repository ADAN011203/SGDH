package com.example.sgdh.ui.notifications

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sgdh.R
import com.example.sgdh.databinding.ActivityNotificationListBinding

class NotificationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationListBinding
    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        viewModel.loadNotifications()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NotificationListActivity)
            // adapter = NotificationAdapter() // Crear adapter después
        }
    }

    private fun observeViewModel() {
        // Observar estado del ViewModel
    }
}