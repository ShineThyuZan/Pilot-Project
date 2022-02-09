package com.galaxy_techno.uKnow.domain

import com.galaxy_techno.uKnow.model.dto.*
import com.galaxy_techno.uKnow.presentation.extension.RemoteEvent
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun storeAuthState(isLoggedIn: Boolean)

    suspend fun getAuthState(): Flow<Boolean>

    suspend fun getCategories(): Flow<RemoteEvent<CategoryListDTO>>

    suspend fun getCountries(): Flow<RemoteEvent<CountryListDTO>>

    suspend fun getStates(): Flow<RemoteEvent<StateListDTO>>

    suspend fun getTownships(): Flow<RemoteEvent<TownshipListDTO>>

    suspend fun getStateWithTownships(): Flow<RemoteEvent<StateWithTownshipDTO>>

    suspend fun getSellerMenus(): Flow<RemoteEvent<SellerMenuListDTO>>

    suspend fun getPaymentTypes(): Flow<RemoteEvent<PaymentTypeDTO>>

    suspend fun saveAccessToken(token: String)

    suspend fun getAccessToken(): Flow<String>

    suspend fun saveRefreshToken(token: String)

    suspend fun getRefreshToken(): Flow<String>

    suspend fun fetchRefreshToken(token : String) : Flow<RemoteEvent<RefreshTokenDTO>>
}