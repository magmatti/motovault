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

private const val DATA_STORE_NAME = "UserEmailDataStore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATA_STORE_NAME
)

class UserEmailDataStore(context: Context) {

    private val dataStore = context.dataStore
    val userEmail: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY] ?: DEFAULT_VALUE
    }

    companion object {
        private var INSTANCE: UserEmailDataStore? = null
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        const val DEFAULT_VALUE = ""

        fun getInstance(context: Context): UserEmailDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = UserEmailDataStore(context)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
        }
    }

    suspend fun getUserEmail(): String {
        val preferences = dataStore.data.first()
        return preferences[USER_EMAIL_KEY] ?: DEFAULT_VALUE
    }
}