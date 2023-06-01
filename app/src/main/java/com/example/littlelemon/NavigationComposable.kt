package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val startDestination = if (userDataAvailable(context)) Home.route else Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Home.route) {
            HomeScreen(navController)
        }
        composable(Profile.route) {
            ProfileScreen(navController)
        }
        composable(Onboarding.route) {
            Onboarding(navController)
        }
    }
}

