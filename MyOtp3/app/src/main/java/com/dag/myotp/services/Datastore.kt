package com.dag.myotp.services

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class Datastore constructor(val context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key")

    companion object{
        const val PRIVATE_KEY = "PRIVATE_KEY"
        const val PUBLIC_KEY = "PUBLIC_KEY"
        val PRIVATE_KEY_PREF = stringPreferencesKey(PRIVATE_KEY)
        val PUBLIC_KEY_PREF = stringPreferencesKey(PUBLIC_KEY)

    }

    fun <T> read(key: Preferences.Key<T>) = context.dataStore.data.map { preferences ->
        preferences[key]
    }

    suspend fun <N> save(key:Preferences.Key<N>, value:N){
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }


}