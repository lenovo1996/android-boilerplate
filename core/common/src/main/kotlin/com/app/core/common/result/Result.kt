package com.app.core.common.result

/**
 * A generic result wrapper for operations that can succeed or fail.
 * Replaces the traditional Either<L, R> pattern with more idiomatic Kotlin.
 */
sealed interface AppResult<out T> {

  data class Success<T>(val data: T) : AppResult<T>

  data class Error(val error: AppError) : AppResult<Nothing>

  val isSuccess: Boolean get() = this is Success
  val isError: Boolean get() = this is Error

  fun getOrNull(): T? = when (this) {
    is Success -> data
    is Error -> null
  }

  fun errorOrNull(): AppError? = when (this) {
    is Success -> null
    is Error -> error
  }
}

/**
 * Represents domain-level errors.
 */
sealed class AppError(
  open val message: String,
  open val cause: Throwable? = null,
) {
  data class Network(
    override val message: String = "Network error",
    override val cause: Throwable? = null,
  ) : AppError(message, cause)

  data class Server(
    val code: String,
    val httpCode: Int = 0,
    override val message: String = "Server error",
    override val cause: Throwable? = null,
  ) : AppError(message, cause)

  data class NoConnection(
    override val message: String = "No internet connection",
  ) : AppError(message)

  data class Unknown(
    override val message: String = "Unknown error",
    override val cause: Throwable? = null,
  ) : AppError(message, cause)
}

/**
 * Maps the success value of an [AppResult].
 */
inline fun <T, R> AppResult<T>.map(transform: (T) -> R): AppResult<R> = when (this) {
  is AppResult.Success -> AppResult.Success(transform(data))
  is AppResult.Error -> this
}

/**
 * FlatMaps the success value of an [AppResult].
 */
inline fun <T, R> AppResult<T>.flatMap(transform: (T) -> AppResult<R>): AppResult<R> = when (this) {
  is AppResult.Success -> transform(data)
  is AppResult.Error -> this
}

/**
 * Returns the success value or the result of [defaultValue] on error.
 */
inline fun <T> AppResult<T>.getOrElse(defaultValue: (AppError) -> T): T = when (this) {
  is AppResult.Success -> data
  is AppResult.Error -> defaultValue(error)
}

/**
 * Performs [action] if this is a success.
 */
inline fun <T> AppResult<T>.onSuccess(action: (T) -> Unit): AppResult<T> {
  if (this is AppResult.Success) action(data)
  return this
}

/**
 * Performs [action] if this is an error.
 */
inline fun <T> AppResult<T>.onError(action: (AppError) -> Unit): AppResult<T> {
  if (this is AppResult.Error) action(error)
  return this
}

/**
 * Converts a suspend block into an [AppResult], catching exceptions.
 */
suspend fun <T> runCatching(block: suspend () -> T): AppResult<T> {
  return try {
    AppResult.Success(block())
  } catch (e: Exception) {
    AppResult.Error(AppError.Unknown(message = e.message ?: "Unknown error", cause = e))
  }
}
