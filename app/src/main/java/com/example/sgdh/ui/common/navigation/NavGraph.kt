package com.example.sgdh.ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sgdh.ui.MainScreen
import com.example.sgdh.ui.auth.LoginScreen
import com.example.sgdh.ui.solicitudes.create.CreateSolicitudScreen
import com.example.sgdh.ui.solicitudes.detail.SolicitudDetailScreen
import com.example.sgdh.ui.solicitudes.list.SolicitudListScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.Login
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Login) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screens.Main) {
                        popUpTo(Screens.Login) { inclusive = true }
                    }
                }
            )
        }

        composable(Screens.Main) {
            MainScreen(
                navController = navController,
                onNavigateToSolicitudes = {
                    navController.navigate(Screens.SolicitudList)
                },
                onNavigateToCreateSolicitud = {
                    navController.navigate(Screens.CreateSolicitud)
                },
                onLogout = {
                    navController.navigate(Screens.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screens.SolicitudList) {
            SolicitudListScreen(
                onSolicitudClick = { solicitudId ->
                    navController.navigate("${Screens.SolicitudDetail}/$solicitudId")
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screens.CreateSolicitud) {
            CreateSolicitudScreen(
                onSolicitudCreated = {
                    navController.popBackStack()
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("${Screens.SolicitudDetail}/{solicitudId}") { backStackEntry ->
            val solicitudId = backStackEntry.arguments?.getString("solicitudId")?.toIntOrNull()
            SolicitudDetailScreen(
                solicitudId = solicitudId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

object Screens {
    const val Login = "login"
    const val Main = "main"
    const val SolicitudList = "solicitudes"
    const val CreateSolicitud = "solicitudes/create"
    const val SolicitudDetail = "solicitudes/detail"
}