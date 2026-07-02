package com.app.boilerplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.boilerplate.navigation.AppNavigation
import com.app.core.ui.theme.BoilerplateTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-activity host for the application.
 * Uses Compose for all UI rendering and Navigation Compose for screen management.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    setContent {
      BoilerplateTheme {
        AppNavigation()
      }
    }
  }
}
