package com.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.core.domain.repository.ConnectivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the Home screen.
 * Demonstrates Hilt injection, StateFlow, and connectivity observation.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
  connectivityRepository: ConnectivityRepository,
) : ViewModel() {

  val uiState: StateFlow<HomeUiState> = connectivityRepository
    .observeConnectivity()
    .map { isConnected ->
      HomeUiState.Success(
        isConnected = isConnected,
        greeting = "Welcome to Boilerplate!",
      )
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = HomeUiState.Loading,
    )
}
