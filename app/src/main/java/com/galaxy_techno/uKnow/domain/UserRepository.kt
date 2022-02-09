package com.galaxy_techno.uKnow.domain

import androidx.paging.PagingData
import com.galaxy_techno.uKnow.model.dto.*
import com.galaxy_techno.uKnow.model.entity.User
import com.galaxy_techno.uKnow.movie.Movie
import com.galaxy_techno.uKnow.presentation.extension.RemoteEvent
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface UserRepository {

    //test
    suspend fun getPagingMovies() : Flow<PagingData<Movie>>

    //remote
    suspend fun uploadAvatar(
        id: Int,
        type: String,
        image: MultipartBody.Part
    ): Flow<RemoteEvent<DefaultResponse>>

    suspend fun requestOTP(
        mobile: String
    ): Flow<RemoteEvent<OTPRequestDTO>>

    suspend fun validateOTP(
        mobile: String,
        otpCode: String
    ): Flow<RemoteEvent<OTPValidateDTO>>

    suspend fun register(
        businessType: Int,
        countryId: Int,
        name: String,
        email: String,
        mobile: String,
        password: String
    ): Flow<RemoteEvent<RegisterDTO>>

    suspend fun login(
        mobile: String,
        password: String
    ): Flow<RemoteEvent<LoginDTO>>


    //persistence
    suspend fun saveUser(user: User)

    suspend fun getUser(): Flow<User>


}