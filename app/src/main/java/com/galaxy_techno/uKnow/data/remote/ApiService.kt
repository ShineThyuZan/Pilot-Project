package com.galaxy_techno.uKnow.data.remote

import com.galaxy_techno.uKnow.common.Endpoint
import com.galaxy_techno.uKnow.model.dto.*
import com.galaxy_techno.uKnow.model.req.DefaultRequest
import com.galaxy_techno.uKnow.model.req.EmployeeRegisterRequest
import com.galaxy_techno.uKnow.movie.ListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    //test
    @GET("https://api.themoviedb.org/3/movie/upcoming?api_key=cdbea55de27a909b4aaa2cfc02eabb75")
    suspend fun fetchMovies(
        @Query("page") pageNumber: Int,
    ): Response<ListResponse>

    /**
    Sample
     */

    @GET("something")
    suspend fun getSomething(
        @Query("someQuery") someQuery: String
    ): Response<DefaultResponse>

    @GET("something/{path}/request")
    suspend fun getSomePath(
        @Part path: String,
        @Query("someQuery") someQuery: String
    ): Response<DefaultResponse>

    @FormUrlEncoded
    @POST("something/{path}")
    suspend fun postSomething(
        @Field("id") id: Int,
        @Field("something") something: String,
        @Path("path") path: String,
        @Query("query") query: String
    ): Response<DefaultResponse>

    @POST("something")
    suspend fun postSomeBody(
        @Body body1: DefaultRequest,
        @Body body2: DefaultRequest
    ): Response<DefaultResponse>

    @Multipart
    @POST("upload-image")
    suspend fun uploadImage(
        @Part("sellerId") id: RequestBody,
        @Part("avatar-or-background") type: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<DefaultResponse>

    /**
    End
     */

    //MASTER
    @GET(Endpoint.REFRESH_TOKEN)
    suspend fun refreshToken(
        @Header(Endpoint.AUTHORIZATION) token: String
    ): Response<RefreshTokenDTO>

    @GET(Endpoint.MASTER_CATEGORY_LIST)
    suspend fun getCategories(): Response<CategoryListDTO>

    @GET(Endpoint.MASTER_COUNTRY_LIST)
    suspend fun getCountries(): Response<CountryListDTO>

    @GET(Endpoint.MASTER_STATE_LIST)
    suspend fun getStates(): Response<StateListDTO>

    @GET(Endpoint.MASTER_TOWNSHIP_LIST)
    suspend fun getTownships(): Response<TownshipListDTO>

    @GET(Endpoint.MASTER_STATE_WITH_TOWNSHIP_LIST)
    suspend fun getStateWithTownships(): Response<StateWithTownshipDTO>

    @GET(Endpoint.MASTER_SELLER_MENU_LIST)
    suspend fun getSellerMenus(): Response<SellerMenuListDTO>

    @GET(Endpoint.MASTER_PAYMENT_TYPE_LIST)
    suspend fun getPaymentTypes(): Response<PaymentTypeDTO>

    //PROFILE
    @FormUrlEncoded
    @POST(Endpoint.SAVE_ADDRESS_INFO)
    suspend fun saveAddressInfo(
        @Field("sellerId") sellerId: Int,
        @Field("perCountryId") perCountryId: Int? = null,
        @Field("perStateId") perStateId: Int? = null,
        @Field("perTownshipId") perTownshipId: Int? = null,
        @Field("perFullAddress") perAddressId: String? = null,
        @Field("shopCountryId") shopCountryId: Int? = null,
        @Field("shopStateId") shopStateId: Int? = null,
        @Field("shopTownshipId") shopTownshipId: Int? = null,
        @Field("shopFullAddress") shopAddress: String? = null,
        @Field("returnCountryId") returnCountryId: Int? = null,
        @Field("returnStateId") returnStateId: Int? = null,
        @Field("returnTownshipId") returnTownshipId: Int? = null,
        @Field("returnFullAddress") returnAddress: String? = null,
    )

    @FormUrlEncoded
    @POST(Endpoint.SAVE_PAYMENT_INFO)
    suspend fun savePaymentInfo(
        @Field("sellerId") sellerId: Int,
        @Field("primaryAccountTypeId") primaryAccountTypeId: Int? = null,
        @Field("primaryAccountNo") primaryAccountNo: String? = null,
        @Field("primaryAccountName") primaryAccountName: String? = null,
        @Field("secondaryAccountTypeId") secondaryAccountTypeId: Int? = null,
        @Field("secondaryAccountNo") secondaryAccountNo: String? = null,
        @Field("secondaryAccountName") secondaryAccountName: String? = null,
    )

    @Multipart
    @FormUrlEncoded
    @POST(Endpoint.SAVE_BUSINESS_INFO)
    suspend fun saveBusinessInfo(
        @Field("sellerId") sellerId: Int,
        @Field("nrcNumber") nrcNumber: String? = null,
        @Part("nrcFront") nrcFrontPicture: MultipartBody.Part? = null,
        @Part("nrcBack") nrcBackPicture: MultipartBody.Part? = null,
        @Field("businessLicenseNumber") businessLicenseNumber: String? = null,
        @Part("businessLicenseFront") businessLicenseFrontPicture: MultipartBody.Part? = null,
        @Part("businessLicenseBack") businessLicenseBack: MultipartBody.Part? = null,
    )

    //PROFILE
    @FormUrlEncoded
    @POST(Endpoint.REGISTER_ADDRESS_INFO)
    suspend fun registerAddressInfo(
        //sellerId != null
        @Field("sellerId") sellerId: Int,
        @Field("perCountryId") perCountryId: Int,
        @Field("perStateId") perStateId: Int,
        @Field("perTownshipId") perTownshipId: Int,
        @Field("perFullAddress") perAddressId: String,
        @Field("shopCountryId") shopCountryId: Int,
        @Field("shopStateId") shopStateId: Int,
        @Field("shopTownshipId") shopTownshipId: Int,
        @Field("shopFullAddress") shopAddress: String,
        @Field("returnCountryId") returnCountryId: Int,
        @Field("returnStateId") returnStateId: Int,
        @Field("returnTownshipId") returnTownshipId: Int,
        @Field("returnFullAddress") returnAddress: String,
    )

    @FormUrlEncoded
    @POST(Endpoint.REGISTER_PAYMENT_INFO)
    suspend fun registerPaymentInfo(
        @Field("sellerId") sellerId: Int,
        @Field("primaryAccountTypeId") primaryAccountTypeId: Int,
        @Field("primaryAccountNo") primaryAccountNo: String,
        @Field("primaryAccountName") primaryAccountName: String,
        @Field("secondaryAccountTypeId") secondaryAccountTypeId: Int,
        @Field("secondaryAccountNo") secondaryAccountNo: String,
        @Field("secondaryAccountName") secondaryAccountName: String
    )

    @Multipart
    @FormUrlEncoded
    @POST(Endpoint.REGISTER_BUSINESS_INFO)
    suspend fun registerBusinessInfo(
        @Field("sellerId") sellerId: Int,
        @Field("nrcNumber") nrcNumber: String,
        @Part("nrcFront") nrcFrontPicture: MultipartBody.Part,
        @Part("nrcBack") nrcBackPicture: MultipartBody.Part,
        @Field("businessLicenseNumber") businessLicenseNumber: String,
        @Part("businessLicenseFront") businessLicenseFrontPicture: MultipartBody.Part,
        @Part("businessLicenseBack") businessLicenseBack: MultipartBody.Part
    )

    @FormUrlEncoded
    @POST(Endpoint.RELOAD_ADDRESS_INFO)
    suspend fun reloadAddressInfo(
        @Field("sellerId") sellerId: Int
    )

    @FormUrlEncoded
    @POST(Endpoint.RELOAD_PAYMENT_INFO)
    suspend fun reloadPaymentInfo(
        @Field("sellerId") sellerId: Int
    )

    @FormUrlEncoded
    @POST(Endpoint.RELOAD_BUSINESS_INFO)
    suspend fun reloadBusinessInfo(
        @Field("sellerId") sellerId: Int
    )

    @FormUrlEncoded
    @POST(Endpoint.GET_OTP)
    suspend fun requestOTP(
        @Field("mobile") mobileNumber: String
    ): Response<OTPRequestDTO>

    @FormUrlEncoded
    @POST(Endpoint.VALIDATE_OTP)
    suspend fun validateOTP(
        @Field("mobile") mobileNumber: String,
        @Field("otpCode") otpCode: String
    ): Response<OTPValidateDTO>

    //Auth
    @FormUrlEncoded
    @POST(Endpoint.REGISTER)
    suspend fun register(
        @Field("accountType") businessType: Int,
        @Field("baseCountryId") countryId: Int,
        @Field("shopName") name: String,
        @Field("email") email: String,
        @Field("mobile") mobileNumber: String,
        @Field("password") password: String
    ): Response<RegisterDTO>

    @POST(Endpoint.EMPLOYEE_REGISTER)
    suspend fun registerEmployee(
        @Body request: EmployeeRegisterRequest
    ): Response<EmployeeRegisterDTO>

    @FormUrlEncoded
    @POST(Endpoint.LOGIN)
    suspend fun login(
        @Field("mobile") mobileNumber: String,
        @Field("password") token: String
    ): Response<LoginDTO>

}