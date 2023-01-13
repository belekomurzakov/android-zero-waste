package omurzakov.zerowaste.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    companion object {
        private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore("user")
        val NUMBER = stringPreferencesKey("number")
    }

    fun getNumber() = context.userPreferencesDataStore.data
        .map { it[NUMBER] ?: "0" }
        .flowOn(Dispatchers.IO)

    suspend fun saveNumber(count: String) =
        context.userPreferencesDataStore.edit { it[NUMBER] = count }
}