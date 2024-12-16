package com.uken.motovault.datastores

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "OilChangeIntervalDataStore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATA_STORE_NAME
)

class OilChangeIntervalDataStore(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private var INSTANCE: OilChangeIntervalDataStore? = null
        val OIL_CHANGE_KEY = stringPreferencesKey("oil_change_interval")
        const val DEFAULT_VALUE = "Every year"

        fun getInstance(context: Context): OilChangeIntervalDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = OilChangeIntervalDataStore(context)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun saveOilChangeInterval(interval: String) {
        dataStore.edit { preferences ->
            preferences[OIL_CHANGE_KEY] = interval
        }
    }

    suspend fun getLastOilChangeInterval(): String {
        val preferences = dataStore.data.first()
        return preferences[OIL_CHANGE_KEY] ?: DEFAULT_VALUE
    }

    val oilChangeInterval: Flow<String> = dataStore.data.map { preferences ->
        preferences[OIL_CHANGE_KEY] ?: DEFAULT_VALUE
    }
}