package com.app.core.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for network connectivity status.
 */
interface ConnectivityRepository {
  /**
   * Observes the current network connectivity state.
   * Emits true when connected, false when disconnected.
   */
  fun observeConnectivity(): Flow<Boolean>

  /**
   * Returns the current connectivity state.
   */
  fun isConnected(): Boolean
}
