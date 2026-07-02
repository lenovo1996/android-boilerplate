package com.app.boilerplate

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Application class with Hilt dependency injection.
 */
@HiltAndroidApp
class BoilerplateApp : Application() {

  override fun onCreate() {
    super.onCreate()
    initTimber()
    initStrictMode()
  }

  private fun initTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    // In production, plant a crash-reporting tree (e.g., Firebase Crashlytics)
  }

  private fun initStrictMode() {
    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
          .detectAll()
          .permitDiskReads()
          .penaltyLog()
          .build(),
      )
      StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
          .detectLeakedSqlLiteObjects()
          .detectLeakedClosableObjects()
          .penaltyLog()
          .build(),
      )
    }
  }
}
