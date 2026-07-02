package com.app.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

/**
 * Wrapper around Jetpack DataStore for type-safe preferences.
 * Replaces SharedPreferences with coroutine-based, non-blocking API.
 */
@Singleton
class AppPreferences @Inject constructor(
  @ApplicationContext private val context: Context,
) {

  suspend fun saveString(key: String, value: String) {
    context.dataStore.edit { preferences ->
      preferences[stringPreferencesKey(key)] = value
    }
  }

  fun readString(key: String): Flow<String?> {
    return context.dataStore.data.map { preferences ->
      preferences[stringPreferencesKey(key)]
    }
  }

  suspend fun remove(key: String) {
    context.dataStore.edit { preferences ->
      preferences.remove(stringPreferencesKey(key))
    }
  }

  suspend fun clearAll() {
    context.dataStore.edit { it.clear() }
  }
}
