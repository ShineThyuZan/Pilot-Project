package com.galaxy_techno.uKnow.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.galaxy_techno.uKnow.common.RemoteDataSource
import com.galaxy_techno.uKnow.data.db.DbDataSource
import com.galaxy_techno.uKnow.data.ds.DsDataSource
import com.galaxy_techno.uKnow.data.remote.ApiDataSource
import com.galaxy_techno.uKnow.data.remote.ApiService
import com.galaxy_techno.uKnow.di.Qualifier
import com.galaxy_techno.uKnow.model.dto.*
import com.galaxy_techno.uKnow.model.entity.User
import com.galaxy_techno.uKnow.movie.Movie
import com.galaxy_techno.uKnow.movie.MoviePagingDataSource
import com.galaxy_techno.uKnow.presentation.extension.RemoteEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

open class UserRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DbDataSource,
    private val dsDataSource: DsDataSource,
    @Qualifier.Io private val io: CoroutineDispatcher,
    private val apiService: ApiService,
) : UserRepository, RemoteDataSource() {

    //test
    override suspend fun getPagingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviePagingDataSource.PAGE_SIZE, // mandatory (others are optional)
//                maxSize = MoviePagingDataSource.MAX_SIZE, //cache size
//                initialLoadSize = MoviePagingDataSource.INITIAL_LOAD_SIZE, //initial load
//                prefetchDistance = 2,
//                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingDataSource(apiService) }
        ).flow
    }



    override suspend fun requestOTP(mobile: String): Flow<RemoteEvent<OTPRequestDTO>> {

        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchRequestOTP(mobile)
                }
            )
        }.flowOn(io)
    }

    override suspend fun validateOTP(
        mobile: String,
        otpCode: String
    ): Flow<RemoteEvent<OTPValidateDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchValidateOTP(
                        mobile,
                        otpCode
                    )
                }
            )
        }.flowOn(io)
    }

    override suspend fun register(
        businessType: Int,
        countryId: Int,
        name: String,
        email: String,
        mobile: String,
        password: String
    ): Flow<RemoteEvent<RegisterDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchRegister(
                        businessType,
                        countryId,
                        name,
                        email,
                        mobile,
                        password
                    )
                }
            )
        }.flowOn(io)
    }

    override suspend fun login(
        mobile: String,
        password: String
    ): Flow<RemoteEvent<LoginDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.fetchLogin(
                        mobile,
                        password
                    )
                }
            )
        }.flowOn(io)
    }

    override suspend fun uploadAvatar(
        id: Int,
        type: String,
        image: MultipartBody.Part
    ): Flow<RemoteEvent<DefaultResponse>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.uploadAvatar(
                        id, type, image
                    )
                }

            )
        }.flowOn(io)
    }

    override suspend fun saveUser(user: User) {
        withContext(io) {
            dbDataSource.saveUser(user)
        }
    }

    override suspend fun getUser(): Flow<User> {
        return flow {
            emit(
                dbDataSource.getUser()
            )
        }.flowOn(io)
    }
}