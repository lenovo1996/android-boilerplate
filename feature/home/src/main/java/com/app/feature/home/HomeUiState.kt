package com.app.feature.home

/**
 * Represents the UI state for the Home screen.
 */
sealed interface HomeUiState {

  /** Initial loading state. */
  data object Loading : HomeUiState

  /** Content successfully loaded. */
  data class Success(
    val isConnected: Boolean,
    val greeting: String,
  ) : HomeUiState

  /** Error occurred. */
  data class Error(
    val message: String,
  ) : HomeUiState
}
