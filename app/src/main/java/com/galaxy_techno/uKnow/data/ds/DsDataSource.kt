package com.galaxy_techno.uKnow.data.ds

import kotlinx.coroutines.flow.Flow

interface DsDataSource {
    suspend fun putAuthState(isLoggedIn : Boolean)
    suspend fun pullAuthState() : Flow<Boolean>

    suspend fun putAccessToken(token : String)
    suspend fun pullAccessToken() : Flow<String>

    suspend fun putRefreshToken(token : String)
    suspend fun pullRefreshToken() : Flow<String>
}