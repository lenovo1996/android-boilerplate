package com.app.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.feature.home.HomeScreen

const val HOME_ROUTE = "home"

/**
 * Navigation extension to add the Home screen to the nav graph.
 */
fun NavGraphBuilder.homeScreen() {
  composable(route = HOME_ROUTE) {
    HomeScreen()
  }
}

/**
 * Navigation extension to navigate to the Home screen.
 */
fun NavController.navigateToHome() {
  navigate(HOME_ROUTE) {
    popUpTo(0) { inclusive = true }
    launchSingleTop = true
  }
}
