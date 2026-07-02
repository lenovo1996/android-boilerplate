package com.app.feature.home

import app.cash.turbine.test
import com.app.core.domain.repository.ConnectivityRepository
import com.app.core.testing.MainDispatcherExtension
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertIs

@ExtendWith(MockKExtension::class, MainDispatcherExtension::class)
class HomeViewModelTest {

  @MockK
  private lateinit var connectivityRepository: ConnectivityRepository

  private lateinit var viewModel: HomeViewModel

  @BeforeEach
  fun setUp() {
    every { connectivityRepository.observeConnectivity() } returns flowOf(true)
    viewModel = HomeViewModel(connectivityRepository)
  }

  @Test
  fun `initial state is Loading`() = runTest {
    // Given fresh ViewModel with delayed emission
    every { connectivityRepository.observeConnectivity() } returns flowOf()
    val vm = HomeViewModel(connectivityRepository)

    // Then initial value should be Loading
    assertIs<HomeUiState.Loading>(vm.uiState.value)
  }

  @Test
  fun `emits Success when connected`() = runTest {
    viewModel.uiState.test {
      val state = awaitItem()
      assertIs<HomeUiState.Success>(state)
      assertEquals(true, state.isConnected)
      assertEquals("Welcome to Boilerplate!", state.greeting)
    }
  }

  @Test
  fun `emits Success with disconnected when no connection`() = runTest {
    every { connectivityRepository.observeConnectivity() } returns flowOf(false)
    val vm = HomeViewModel(connectivityRepository)

    vm.uiState.test {
      val state = awaitItem()
      assertIs<HomeUiState.Success>(state)
      assertEquals(false, state.isConnected)
    }
  }
}
