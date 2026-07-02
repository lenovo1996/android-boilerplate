package com.app.core.network.interceptor

import com.app.core.network.model.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Intercepts network requests and converts IOExceptions into
 * structured [NetworkException.NoConnection].
 */
class ConnectivityInterceptor @Inject constructor() : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    return try {
      chain.proceed(chain.request())
    } catch (e: NetworkException) {
      throw e
    } catch (_: IOException) {
      throw NetworkException.NoConnection
    }
  }
}
