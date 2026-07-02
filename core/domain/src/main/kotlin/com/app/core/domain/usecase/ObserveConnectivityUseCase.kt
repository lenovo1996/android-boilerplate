package com.app.core.domain.usecase

import com.app.core.domain.repository.ConnectivityRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to observe network connectivity changes.
 */
class ObserveConnectivityUseCase(
  private val connectivityRepository: ConnectivityRepository,
) : FlowUseCase<Unit, Boolean>() {

  override fun execute(input: Unit): Flow<Boolean> {
    return connectivityRepository.observeConnectivity()
  }
}
