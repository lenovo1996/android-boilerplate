package com.app.core.network.model

import java.io.IOException

/**
 * Sealed class representing network-layer exceptions.
 */
sealed class NetworkException(message: String, cause: Throwable? = null) : IOException(message, cause) {

  /** No internet connection available. */
  data object NoConnection : NetworkException("No internet connection")

  /** Request timed out. */
  data object Timeout : NetworkException("Request timed out")

  /** Server returned an error response (4xx/5xx). */
  data class Server(
    val code: String,
    override val message: String,
    val httpCode: Int = 0,
  ) : NetworkException(message)

  /** Unknown/unexpected network error. */
  data class Unknown(
    override val message: String = "Unknown network error",
    override val cause: Throwable? = null,
  ) : NetworkException(message, cause)
}
