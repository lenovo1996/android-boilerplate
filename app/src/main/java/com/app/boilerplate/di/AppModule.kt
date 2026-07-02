package com.app.boilerplate.di

import com.app.boilerplate.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * App-level Hilt module providing app-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  @Named("base_url")
  fun providesBaseUrl(): String = BuildConfig.BASE_URL
}
