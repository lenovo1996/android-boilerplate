package com.app.core.network.interceptor

import com.app.core.network.model.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Intercepts HTTP responses to handle server errors (4xx/5xx).
 * Parses error body and throws structured [NetworkException.Server].
 */
class ServerInterceptor @Inject constructor() : Interceptor {

  @Throws(NetworkException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    return try {
      val response = chain.proceed(request)
      val httpCode = response.code

      if (httpCode in 400..599) {
        val errorBody = response.peekBody(Long.MAX_VALUE).string()
        val (code, message) = parseError(errorBody)

        Timber.e("HTTP $httpCode | ${request.method} ${request.url} | $code: $message")

        throw NetworkException.Server(
          code = code,
          message = message,
          httpCode = httpCode,
        )
      }

      response
    } catch (e: NetworkException) {
      throw e
    } catch (_: UnknownHostException) {
      throw NetworkException.NoConnection
    } catch (_: SocketTimeoutException) {
      throw NetworkException.Timeout
    } catch (_: IOException) {
      throw NetworkException.NoConnection
    } catch (e: Exception) {
      throw NetworkException.Unknown(cause = e)
    }
  }

  private fun parseError(body: String): Pair<String, String> {
    return try {
      val json = JSONObject(body)
      val code = json.optString("code", "UNKNOWN")
      val message = json.optString("message", "Server error")
      code to message
    } catch (_: Exception) {
      "UNKNOWN" to "Failed to parse error response"
    }
  }
}
