package com.galaxy_techno.uKnow.domain

import com.galaxy_techno.uKnow.common.RemoteDataSource
import com.galaxy_techno.uKnow.data.db.DbDataSource
import com.galaxy_techno.uKnow.data.ds.DsDataSource
import com.galaxy_techno.uKnow.data.remote.ApiDataSource
import com.galaxy_techno.uKnow.di.Qualifier
import com.galaxy_techno.uKnow.model.dto.*
import com.galaxy_techno.uKnow.presentation.extension.RemoteEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DbDataSource,
    private val dsDataSource: DsDataSource,
    @Qualifier.Io private val io: CoroutineDispatcher
) : AppRepository, RemoteDataSource() {


    override suspend fun storeAuthState(isLoggedIn: Boolean) {
        withContext(io)
        {
            dsDataSource.putAuthState(isLoggedIn)
        }
    }

    override suspend fun getAuthState(): Flow<Boolean> {
        return dsDataSource.pullAuthState()
    }

    override suspend fun getCategories(): Flow<RemoteEvent<CategoryListDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchCategoryList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getCountries(): Flow<RemoteEvent<CountryListDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchCountryList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getStates(): Flow<RemoteEvent<StateListDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchStateList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getTownships(): Flow<RemoteEvent<TownshipListDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchTownshipList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getStateWithTownships(): Flow<RemoteEvent<StateWithTownshipDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchStateWithTownshipList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getSellerMenus(): Flow<RemoteEvent<SellerMenuListDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchSellerMenuList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getPaymentTypes(): Flow<RemoteEvent<PaymentTypeDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchPaymentTypeList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun saveAccessToken(token: String) {
        withContext(io) {
            dsDataSource.putAccessToken(token)
        }
    }

    override suspend fun getAccessToken(): Flow<String> {
        return dsDataSource.pullAccessToken()
    }

    override suspend fun saveRefreshToken(token: String) {
        withContext(io) {
            dsDataSource.putRefreshToken(token)
        }
    }

    override suspend fun getRefreshToken(): Flow<String> {
        return dsDataSource.pullRefreshToken()
    }

    override suspend fun fetchRefreshToken(token: String): Flow<RemoteEvent<RefreshTokenDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchRefreshToken(token)
                }
            )
        }.flowOn(io)
    }
}
