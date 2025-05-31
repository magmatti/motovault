package com.uken.motovault.datastores

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "NotificationsDataStore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATA_STORE_NAME
)

class NotificationsDataStore(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private var INSTANCE: NotificationsDataStore? = null
        val NOTIFICATION_KEY = booleanPreferencesKey("notification_key")
        const val DEFAULT_VALUE = true

        fun getInstance(context: Context): NotificationsDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = NotificationsDataStore(context)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun saveOilChangeInterval(enableNotifications: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = enableNotifications
        }
    }

    suspend fun getNotificationsEnabled(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[NOTIFICATION_KEY] ?: DEFAULT_VALUE
    }

    val areNotificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[NOTIFICATION_KEY] ?: DEFAULT_VALUE
    }
}