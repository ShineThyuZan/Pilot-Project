package com.galaxy_techno.uKnow.data.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

open class DsDataSourceImpl @Inject constructor(
    private val ds: DataStore<Preferences>
) : DsDataSource {

    companion object {
        val AUTH_STATE = booleanPreferencesKey("com.galaxy_techno.auth_state")
        val ACCESS_TOKEN = stringPreferencesKey("com.galaxy_techno.access_token")
        val REFRESH_TOKEN = stringPreferencesKey("com.galaxy_techno.refresh_token")
    }

    override suspend fun putAuthState(isLoggedIn: Boolean) {
        ds.edit {
            it[AUTH_STATE] = isLoggedIn
        }
    }

    override suspend fun pullAuthState(): Flow<Boolean> {
        return ds.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception

            }.map {
                it[AUTH_STATE] ?: true
            }
    }

    override suspend fun putAccessToken(token: String) {
        ds.edit {
            it[ACCESS_TOKEN] = token
        }
    }

    override suspend fun pullAccessToken(): Flow<String> {
        return ds.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map {
            it[ACCESS_TOKEN] ?: "empty_access_token"
        }
    }

    override suspend fun putRefreshToken(token: String) {
        ds.edit {
            it[REFRESH_TOKEN] = token
        }
    }

    override suspend fun pullRefreshToken(): Flow<String> {
        return ds.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map {
            it[REFRESH_TOKEN] ?: "empty_refresh_token"
        }
    }
}