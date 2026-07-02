package com.app.boilerplate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.app.feature.home.navigation.HOME_ROUTE
import com.app.feature.home.navigation.homeScreen

/**
 * Root navigation graph for the application.
 * Each feature module contributes its own screens via NavGraphBuilder extensions.
 */
@Composable
fun AppNavigation() {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = HOME_ROUTE,
  ) {
    homeScreen()
    // Add more feature navigation here:
    // settingsScreen(navController)
    // profileScreen(navController)
  }
}
