/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Preferences(context: Context) {
    private val dataStore: DataStore<Preferences> =
        context.createDataStore(name = "app_preference")

    private val keyUsername = preferencesKey<String>(name = "username")

    suspend fun saveUsername(username: String) {
        dataStore.edit {
            it[keyUsername] = username
        }
    }

    fun getUsername(): Flow<String> {
        return dataStore.data.map {
            it[keyUsername] ?: "username"
        }

    }
}