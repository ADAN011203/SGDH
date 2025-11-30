package com.example.sgdh.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.sgdh.data.local.PreferencesManager
import com.example.sgdh.ui.common.navigation.NavGraph
import com.example.sgdh.ui.common.navigation.Screens
import com.example.sgdh.ui.theme.SgdhTheme

// REMOVER: @AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesManager = PreferencesManager(this)

        setContent {
            SgdhTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val startDestination = if (preferencesManager.isLoggedIn()) {
                        Screens.Main
                    } else {
                        Screens.Login
                    }

                    NavGraph(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}