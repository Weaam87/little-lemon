package com.example.littlelemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.data.database.MenuItemRoom
import com.example.littlelemon.ui.screens.HomeScreen
import com.example.littlelemon.ui.screens.MenuItemDetailScreen
import com.example.littlelemon.ui.screens.Onboarding
import com.example.littlelemon.ui.screens.OrderSummaryScreen
import com.example.littlelemon.ui.screens.ProfileScreen
import com.example.littlelemon.ui.screens.userDataAvailable

@Composable
fun MyNavigation(menuItems: List<MenuItemRoom>) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val startDestination = if (userDataAvailable(context)) Home.route else Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Home.route) {
            HomeScreen(navController, menuItems)
        }
        composable(Profile.route) {
            ProfileScreen(navController)
        }
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable("${MenuItemDetail.route}/{${MenuItemDetail.idArg}}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString(MenuItemDetail.idArg)?.toIntOrNull() ?: 0
            MenuItemDetailScreen(navController, id)
        }
        composable(OrderSummary.route) {
            OrderSummaryScreen(navController)
        }
    }
}

