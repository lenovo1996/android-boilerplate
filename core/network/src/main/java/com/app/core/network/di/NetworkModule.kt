package com.app.core.network.di

import com.app.core.network.interceptor.ConnectivityInterceptor
import com.app.core.network.interceptor.ServerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val DEFAULT_TIMEOUT_SECONDS = 60L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun providesJson(): Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
  }

  @Provides
  @Singleton
  fun providesLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
      level = if (com.app.core.network.BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    }
  }

  @Provides
  @Singleton
  fun providesOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    serverInterceptor: ServerInterceptor,
    connectivityInterceptor: ConnectivityInterceptor,
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .writeTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
      .addInterceptor(connectivityInterceptor)
      .addInterceptor(serverInterceptor)
      .addInterceptor(loggingInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun providesRetrofit(
    okHttpClient: OkHttpClient,
    json: Json,
    @Named("base_url") baseUrl: String,
  ): Retrofit {
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(baseUrl)
      .addConverterFactory(json.asConverterFactory(contentType))
      .build()
  }
}
