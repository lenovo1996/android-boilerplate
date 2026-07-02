package com.app.core.domain.usecase

import com.app.core.common.result.AppResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Base class for use cases that return a single result via suspend function.
 *
 * @param I Input parameter type
 * @param O Output result type
 */
abstract class UseCase<in I, out O>(
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
  suspend operator fun invoke(input: I): AppResult<O> {
    return withContext(dispatcher) {
      execute(input)
    }
  }

  protected abstract suspend fun execute(input: I): AppResult<O>
}

/**
 * Base class for use cases that return a Flow of results.
 *
 * @param I Input parameter type
 * @param O Output result type
 */
abstract class FlowUseCase<in I, out O> {
  operator fun invoke(input: I): Flow<O> = execute(input)

  protected abstract fun execute(input: I): Flow<O>
}

/**
 * Base class for use cases with no input parameter.
 *
 * @param O Output result type
 */
abstract class NoInputUseCase<out O>(
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
  suspend operator fun invoke(): AppResult<O> {
    return withContext(dispatcher) {
      execute()
    }
  }

  protected abstract suspend fun execute(): AppResult<O>
}
