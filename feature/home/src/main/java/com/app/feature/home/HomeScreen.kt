package com.app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.core.ui.components.ErrorView
import com.app.core.ui.components.LoadingIndicator

/**
 * Home screen composable.
 * Demonstrates ViewModel injection, state collection, and UI state handling.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  viewModel: HomeViewModel = hiltViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Boilerplate") },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
      )
    },
  ) { innerPadding ->
    when (val state = uiState) {
      is HomeUiState.Loading -> {
        LoadingIndicator(modifier = Modifier.padding(innerPadding))
      }
      is HomeUiState.Success -> {
        HomeContent(
          greeting = state.greeting,
          isConnected = state.isConnected,
          modifier = Modifier.padding(innerPadding),
        )
      }
      is HomeUiState.Error -> {
        ErrorView(
          message = state.message,
          modifier = Modifier.padding(innerPadding),
        )
      }
    }
  }
}

@Composable
private fun HomeContent(
  greeting: String,
  isConnected: Boolean,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = greeting,
      style = MaterialTheme.typography.headlineMedium,
      color = MaterialTheme.colorScheme.onSurface,
    )

    Spacer(modifier = Modifier.height(24.dp))

    Icon(
      imageVector = if (isConnected) Icons.Outlined.Wifi else Icons.Outlined.WifiOff,
      contentDescription = if (isConnected) "Connected" else "Disconnected",
      modifier = Modifier.size(48.dp),
      tint = if (isConnected) {
        MaterialTheme.colorScheme.primary
      } else {
        MaterialTheme.colorScheme.error
      },
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = if (isConnected) "Connected to Internet" else "No Internet Connection",
      style = MaterialTheme.typography.bodyLarge,
      color = if (isConnected) {
        MaterialTheme.colorScheme.primary
      } else {
        MaterialTheme.colorScheme.error
      },
    )
  }
}
