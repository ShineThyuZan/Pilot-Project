package com.galaxy_techno.uKnow.data.remote

import com.galaxy_techno.uKnow.model.dto.*
import com.galaxy_techno.uKnow.movie.ListResponse
import com.galaxy_techno.uKnow.util.TypeConverter
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

open class ApiDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : ApiDataSource {

    //test
    override suspend fun fetchMovies(pageNumber: Int): Response<ListResponse> {
        return apiService.fetchMovies(pageNumber)
    }

    override suspend fun fetchRequestOTP(
        mobile: String
    ): Response<OTPRequestDTO> {
        return apiService.requestOTP(
            mobile
        )
    }

    override suspend fun fetchValidateOTP(
        mobile: String,
        otpCode: String
    ): Response<OTPValidateDTO> {
        return apiService.validateOTP(
            mobile,
            otpCode
        )
    }

    override suspend fun fetchRegister(
        businessType: Int,
        countryId: Int,
        name: String,
        email: String,
        mobile: String,
        password: String
    ): Response<RegisterDTO> {
        return apiService.register(
            businessType,
            countryId,
            name,
            email,
            mobile,
            password
        )
    }

    override suspend fun fetchLogin(
        mobile: String,
        password: String
    ): Response<LoginDTO> {
        return apiService.login(
            mobile,
            password
        )
    }

    override suspend fun fetchCategoryList(): Response<CategoryListDTO> {
        return apiService.getCategories()
    }

    override suspend fun fetchCountryList(): Response<CountryListDTO> {
        return apiService.getCountries()
    }

    override suspend fun fetchStateList(): Response<StateListDTO> {
        return apiService.getStates()
    }

    override suspend fun fetchTownshipList(): Response<TownshipListDTO> {
        return apiService.getTownships()
    }

    override suspend fun fetchStateWithTownshipList(): Response<StateWithTownshipDTO> {
        return apiService.getStateWithTownships()
    }

    override suspend fun fetchSellerMenuList(): Response<SellerMenuListDTO> {
        return apiService.getSellerMenus()
    }

    override suspend fun fetchPaymentTypeList(): Response<PaymentTypeDTO> {
        return apiService.getPaymentTypes()
    }

    override suspend fun uploadAvatar(
        id: Int,
        type: String,
        image: MultipartBody.Part
    ): Response<DefaultResponse> {
        return apiService.uploadImage(
            TypeConverter.createPartFromString(id.toString()),
            TypeConverter.createPartFromString(type),
            image
        )
    }

    override suspend fun fetchRefreshToken(
        token: String
    ): Response<RefreshTokenDTO> {
        return apiService.refreshToken(token)
    }
}