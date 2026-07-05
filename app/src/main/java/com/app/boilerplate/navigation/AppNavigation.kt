package com.app.boilerplate.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


private const val START_ROUTE = "start"

/**
 * Root navigation graph for the application.
 * Each feature module contributes its own screens via NavGraphBuilder extensions.
 */
@Composable
fun AppNavigation() {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = START_ROUTE,
  ) {
    composable(START_ROUTE) {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        Text("Welcome to Boilerplate!")
      }
    }
    // Add more feature navigation here:
    // settingsScreen(navController)
    // profileScreen(navController)
  }
}

