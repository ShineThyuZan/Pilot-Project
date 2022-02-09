package com.galaxy_techno.uKnow.data.remote

import com.galaxy_techno.uKnow.model.dto.*
import com.galaxy_techno.uKnow.movie.ListResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface ApiDataSource {
    //test
    suspend fun fetchMovies(
        pageNumber: Int,
    ): Response<ListResponse>

    suspend fun fetchRefreshToken(
        token: String
    ): Response<RefreshTokenDTO>

    suspend fun fetchRequestOTP(
        mobile: String
    ): Response<OTPRequestDTO>

    suspend fun fetchValidateOTP(
        mobile: String,
        otpCode: String
    ): Response<OTPValidateDTO>

    suspend fun fetchRegister(
        businessType: Int,
        countryId: Int,
        name: String,
        email: String,
        mobile: String,
        password: String
    ): Response<RegisterDTO>

    suspend fun fetchLogin(
        mobile: String,
        password: String
    ): Response<LoginDTO>

    suspend fun fetchCategoryList(): Response<CategoryListDTO>

    suspend fun fetchCountryList(): Response<CountryListDTO>

    suspend fun fetchStateList(): Response<StateListDTO>

    suspend fun fetchTownshipList(): Response<TownshipListDTO>

    suspend fun fetchStateWithTownshipList(): Response<StateWithTownshipDTO>

    suspend fun fetchSellerMenuList(): Response<SellerMenuListDTO>

    suspend fun fetchPaymentTypeList(): Response<PaymentTypeDTO>

    suspend fun uploadAvatar(
        id: Int,
        type: String,
        image: MultipartBody.Part
    ): Response<DefaultResponse>


}
