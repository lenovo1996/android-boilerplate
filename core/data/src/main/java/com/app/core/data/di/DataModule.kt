package com.app.core.data.di

import com.app.core.data.repository.ConnectivityRepositoryImpl
import com.app.core.domain.repository.ConnectivityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

  @Binds
  @Singleton
  abstract fun bindsConnectivityRepository(
    impl: ConnectivityRepositoryImpl,
  ): ConnectivityRepository
}
